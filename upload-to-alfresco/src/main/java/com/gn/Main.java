package com.gn;

import java.io.File;

import com.gn.alfresco.Upload;

public class Main {

	public static void main(String[] args) {
		File f = new File("C:/Users/MAtteo/Documents/Progetti/FILA/Documenti di esempio/PAK_00010_15000409_SR.pdf");
		String alf_ticket = Upload.getTicket("admin", "alfresco");
		// FileInputStream is=new FileInputStream(f);
		Upload.uploadDocument(alf_ticket , f, "PAK_00010_15000409_SR.pdf", "application/pdf",
				"description", null);

		// uploadDocument("TICKET_3ef085c4e24f4e2c53a3fa72b3111e55ee6f0543",
		// f,"47.bmp","image
		// file","application/jpg","workspace://SpacesStore/65a06f8c-0b35-4dae-9835-e38414a99bc1");
	}

}
