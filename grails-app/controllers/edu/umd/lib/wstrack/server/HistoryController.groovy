package edu.umd.lib.wstrack.server

import org.springframework.dao.DataIntegrityViolationException

import au.com.bytecode.opencsv.CSVWriter

class HistoryController {

  def filterPaneService
  def grailsApplication

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  def list() {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [historyInstanceList: History.list(params), historyInstanceTotal: History.count(), exportParams:getExportParams(params)]
  }


  def filter = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    render( view:'list',
        model:[ historyInstanceList: filterPaneService.filter( params, History ),
          historyInstanceTotal: filterPaneService.count( params, History ),
          filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params),
          params:params,
          exportParams:getExportParams(params) ] )
  }

  /**
   * Export History rows as CSV, honoring selected sort and filter
   */
  def export = {
    response.contentType = grailsApplication.config.grails.mime.types['csv']
    response.setHeader("Content-disposition", "attachment; filename=wstrack.csv")

    // Open the CSVWriter
    char separator = ','
    char quoteCharacter = '"'
    String lineEnd = CSVWriter.DEFAULT_LINE_END
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(response.outputStream, "UTF-8")

    CSVWriter writer = new CSVWriter(outputStreamWriter, separator, quoteCharacter, lineEnd)

    // add the header
    writer.writeNext(["Timestamp", "Computer Name", "Status", "OS", "User Hash", "Guest Flag"] as String[])

    // stream out the chunked data
    List fields = ["timestamp", "computerName", "status", "os", "userHash", "guestFlag"]

    def exportParams = params.clone()

    def total = filterPaneService.count( exportParams, History )
    def offset = 0l
    def chunk = 100l
    exportParams.max = chunk as String

    while (offset < total) {
      exportParams.offset = offset as String

      List data = filterPaneService.filter( exportParams, History )

      // each row
      data.each { object ->
        List row = []

        fields.each { field ->
          String value = object?."$field"?.toString()
          row.add(value)
        }

        writer.writeNext(row as String[])
      }

      offset += 100l
    }

    writer.flush()
  }

  static def getExportParams(params) {

    Map exportParams = params.clone()

    exportParams.remove('max')
    exportParams.remove('offset')
    exportParams.remove('filter')

    return exportParams
  }

  def create() {
    [historyInstance: new History(params)]
  }

  def save() {
    def historyInstance = new History(params)
    if (!historyInstance.save(flush: true)) {
      render(view: "create", model: [historyInstance: historyInstance])
      return
    }

    flash.message = message(code: 'default.created.message', args: [message(code: 'history.label', default: 'History'), historyInstance.id])
    redirect(action: "show", id: historyInstance.id)
  }

  def show() {
    def historyInstance = History.get(params.id)
    if (!historyInstance) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'history.label', default: 'History'), params.id])
      redirect(action: "list")
      return
    }

    [historyInstance: historyInstance]
  }

  def edit() {
    def historyInstance = History.get(params.id)
    if (!historyInstance) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'history.label', default: 'History'), params.id])
      redirect(action: "list")
      return
    }

    [historyInstance: historyInstance]
  }

  def update() {
    def historyInstance = History.get(params.id)
    if (!historyInstance) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'history.label', default: 'History'), params.id])
      redirect(action: "list")
      return
    }

    if (params.version) {
      def version = params.version.toLong()
      if (historyInstance.version > version) {
        historyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
            [message(code: 'history.label', default: 'History')] as Object[],
            "Another user has updated this History while you were editing")
        render(view: "edit", model: [historyInstance: historyInstance])
        return
      }
    }

    historyInstance.properties = params

    if (!historyInstance.save(flush: true)) {
      render(view: "edit", model: [historyInstance: historyInstance])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'history.label', default: 'History'), historyInstance.id])
    redirect(action: "show", id: historyInstance.id)
  }

  def delete() {
    def historyInstance = History.get(params.id)
    if (!historyInstance) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'history.label', default: 'History'), params.id])
      redirect(action: "list")
      return
    }

    try {
      historyInstance.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'history.label', default: 'History'), params.id])
      redirect(action: "list")
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'history.label', default: 'History'), params.id])
      redirect(action: "show", id: params.id)
    }
  }
}
