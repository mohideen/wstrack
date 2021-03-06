package edu.umd.lib.wstrack.server

import grails.converters.JSON

import java.security.MessageDigest

import sun.misc.BASE64Encoder

class TrackController {

	static allowedMethods = [track: "GET"]

	def track() {

		def result = [status: "success"]

		if(params.status == 'login' || params.status == 'logout') {

			Current current = trackX(params)

			result.current = current

			render result as JSON
		}
		else {
			params.status = 'error'
			render 'Invalid status. Status should be either login or logout.'
		}
	}

	static Current trackX(Map params) {
		
		Boolean guestFlag=false
		
		if(params.guestFlag!=null){
			if(params.guestFlag=='t')
				guestFlag=true
			else
				guestFlag=false
		}else{
			guestFlag=(params.userName.startsWith("libguest"))
		}
		
		if(params.userHash==null){
			params.userHash = generateHash(params.userName)
		}
		
		// Add entry in History
		def history = new History(guestFlag: guestFlag, computerName: params.computerName,os: params.os, status: params.status, userHash : params.userHash)
		history.save()

		// Defining Current Instance which will check if a value exists in the database for a particular IP ( primary Key)
		def current = Current.findByComputerName(params.computerName)

		if(!current) {
			// Create entry in Current
			current = new Current(guestFlag: guestFlag, computerName: params.computerName, os: params.os, status: params.status, userHash : params.userHash)
			if(params.timeStamp!=null){
				current.timestamp = Date.parse("yyyy-MM-dd H:m:s",params.timeStamp)
			}else{
				current.timestamp = history.timestamp
			}
			current.save()

			return current

		} else {

			// Update the already existing entry for that particular IP
			current.setGuestFlag(guestFlag)
			current.setOs(params.os)
			current.setStatus(params.status)
			current.setUserHash(params.userHash)
			if(params.timeStamp!=null){
				current.timestamp = Date.parse("yyyy-MM-dd H:m:s",params.timeStamp)
			}else{
				current.timestamp = history.timestamp
			}
			current.save()

			return current
		}
	}

	public static String generateHash(String input) {
		String hash = "";
		try {
			MessageDigest sha = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = sha.digest(input.getBytes());
			hash = (new BASE64Encoder().encode(hashedBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}

}
