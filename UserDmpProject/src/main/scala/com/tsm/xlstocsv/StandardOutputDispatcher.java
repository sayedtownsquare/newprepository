package com.tsm.xlstocsv;

import java.io.PrintStream;

public class StandardOutputDispatcher implements OutputDispatcher {
    private final PrintStream outputStream = System.out;

    @Override
	public PrintStream openStreamForSheet(String sheetName) {
      return outputStream;
    }

    @Override
	public void closeStreamForSheet(PrintStream stream, String sheetName) {
        // Do nothing as stream is re-used
    }
}