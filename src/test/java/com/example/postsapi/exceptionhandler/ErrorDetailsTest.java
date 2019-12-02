package com.example.postsapi.exceptionhandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ErrorDetailsTest {

    @InjectMocks
    private ErrorDetails errorDetails;

    private Date date;

    @Before
    public void init() {
        date = new Date();

        errorDetails.setTimestamp(date);
        errorDetails.setMessage("Test Error Message");
        errorDetails.setDetails("Test Error Details");
    }

    @Test
    public void ErrorDetails_ErrorDetails_Success() {
        ErrorDetails newErrorDetails = new ErrorDetails(errorDetails.getTimestamp(), "Test Error Message", "Test Error Details");

        assertEquals(errorDetails.getTimestamp(), newErrorDetails.getTimestamp());
        assertEquals(errorDetails.getMessage(), newErrorDetails.getMessage());
        assertEquals(errorDetails.getDetails(), newErrorDetails.getDetails());
    }

    @Test
    public void getTimestamp_Timestamp_Success() {
        assertEquals(date, errorDetails.getTimestamp());
    }

    @Test
    public void setTimestamp_NewTimestamp_Success() {
        Date newDate = new Date();

        errorDetails.setTimestamp(newDate);

        assertEquals(newDate, errorDetails.getTimestamp());
    }

    @Test
    public void getMessage_Message_Success() {
        assertEquals("Test Error Message", errorDetails.getMessage());
    }

    @Test
    public void setMessage_NewMessage_Success() {
        errorDetails.setMessage("New Test Error Message");

        assertEquals("New Test Error Message", errorDetails.getMessage());
    }

    @Test
    public void getDetails_Details_Success() {
        assertEquals("Test Error Details", errorDetails.getDetails());
    }

    @Test
    public void setErrorDetails_NewDetails_Success() {
        errorDetails.setDetails("New Test Error Details");

        assertEquals("New Test Error Details", errorDetails.getDetails());
    }
}
