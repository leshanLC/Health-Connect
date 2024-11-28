package com.example.healthconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthconnect.datamodel.Patient;
import com.example.healthconnect.datamodel.PatientHistory;
import com.example.healthconnect.db.DatabaseHelper;
import com.example.healthconnect.db.PatientDAO;
import com.example.healthconnect.db.PatientHistoryDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ViewPatientHistory extends AppCompatActivity {
    private EditText edtPhn;
    private Button btnPlus, btnBack, btnGeneratePDF;
    private PatientHistoryDAO patientHistoryDAO;
    private ListView lvPatientHistory;
    private SQLiteDatabase database;
    PdfDocument pdfDocument = new PdfDocument();
    private ActivityResultLauncher<Intent> addRecordLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_patient_history);

        btnPlus = findViewById(R.id.btnPlus);
        btnBack = findViewById(R.id.btnBack);
        btnGeneratePDF = findViewById(R.id.btnGeneratePDF);
        edtPhn = findViewById(R.id.edtPhn);
        lvPatientHistory = findViewById(R.id.lvPatientHistory);

        Intent intent = getIntent();
        edtPhn.setText(intent.getStringExtra("phn"));
        edtPhn.setKeyListener(null);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();
        loadPatientHistory(edtPhn.getText().toString().trim());
        //loadPatientHistory(edtPhn.getText().toString().trim());

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ViewPatientHistory.this, AddPatientHistory.class));
                String phn = getIntent().getStringExtra("phn");
                Intent intent = new Intent(ViewPatientHistory.this, AddPatientHistory.class);
                intent.putExtra("phn", phn);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn = edtPhn.getText().toString();
                Intent intent = new Intent(ViewPatientHistory.this, ViewPatient.class);
                intent.putExtra("phn", Integer.parseInt(phn));
                startActivity(intent);
                finish();
            }
        });


        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf();
            }
        });
    }

    private void generatePdf() {
        String phn = edtPhn.getText().toString().trim();

        List<PatientHistory> historyList = patientHistoryDAO.getPatientHistoryByPhn(phn);

        if (historyList.isEmpty()) {
            Toast.makeText(this, "No history found to generate the PDF.", Toast.LENGTH_SHORT).show();
            return;
        }

        PatientDAO patientDAO = new PatientDAO(this);
        Patient patient = patientDAO.getPatientByPhn(Integer.parseInt(phn));

        if (patient == null) {
            Toast.makeText(this, "Patient details not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new PDF document
        PdfDocument pdfDocument = new PdfDocument();

        // Page configuration
        int pageWidth = 500;
        int pageHeight = 800;

        // Styling paints
        Paint titlePaint = new Paint();
        titlePaint.setTextSize(20);
        titlePaint.setFakeBoldText(true);
        titlePaint.setColor(Color.RED);

        Paint headerPaint = new Paint();
        headerPaint.setTextSize(12);
        headerPaint.setFakeBoldText(true);
        headerPaint.setColor(Color.BLACK);

        Paint contentPaint = new Paint();
        contentPaint.setTextSize(10);
        contentPaint.setColor(Color.BLACK);

        Paint boldPaint = new Paint();
        boldPaint.setTextSize(12);
        boldPaint.setFakeBoldText(true);
        boldPaint.setColor(Color.BLACK);

        Paint sectionPaint = new Paint();
        sectionPaint.setTextSize(12);
        sectionPaint.setColor(Color.DKGRAY);

        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.LTGRAY);

        // Footer paint (newly added)
        Paint footerPaint = new Paint();
        footerPaint.setTextSize(10);
        footerPaint.setColor(Color.GRAY);

        // Page and content setup
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int yPosition = 30;

        // Draw the logo in the header
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo); // Assuming the logo is in drawable
        int logoWidth = 80;
        int logoHeight = 80;//(logoBitmap.getHeight() * logoWidth) / logoBitmap.getWidth(); // Scale the logo proportionally
        canvas.drawBitmap(logoBitmap, 20, yPosition, null);
        yPosition += logoHeight + 10;

        // Draw the title "HealthConnect"
        canvas.drawText("HealthConnect", 130, yPosition, titlePaint);
        yPosition += 30;

        // Draw a horizontal line
        canvas.drawLine(10, yPosition, pageWidth - 10, yPosition, linePaint);
        yPosition += 15;

        // Draw the header with patient details
        drawHeaderWithPatientDetails(canvas, headerPaint, linePaint, patient, phn, pageWidth, yPosition);
        yPosition += 50;

        // Draw the records in a user-friendly format
        for (PatientHistory history : historyList) {

            // Draw records using bullet points and bold labels
            canvas.drawText("• Date/Time: " + history.getDateTime(), 40, yPosition, contentPaint);
            yPosition += 20;
            canvas.drawText("• Weight: " + history.getWeight() + " kg", 40, yPosition, contentPaint);
            yPosition += 20;
            canvas.drawText("• Height: " + history.getHeight() + " cm", 40, yPosition, contentPaint);
            yPosition += 20;
            canvas.drawText("• Diagnoses: " + history.getDiagnoses() , 40, yPosition, contentPaint);
            yPosition += 20;
            canvas.drawText("• Treatments: " + history.getTreatments(), 40, yPosition, contentPaint);
            yPosition += 20;

            // Add a separator line between records
            canvas.drawLine(10, yPosition, pageWidth - 10, yPosition, linePaint);
            yPosition += 15;

            // Check if page is full and start a new one if necessary
            if (yPosition > pageHeight - 70) {
                drawFooter(canvas, footerPaint, pageWidth, pageHeight, pdfDocument.getPages().size());
                pdfDocument.finishPage(page);

                // Start a new page
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pdfDocument.getPages().size() + 1).create();
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();
                drawHeaderWithPatientDetails(canvas, headerPaint, linePaint, patient, phn, pageWidth, 30);
                yPosition = 100;
            }
        }

        // Finish the last page
        drawFooter(canvas, footerPaint, pageWidth, pageHeight, pdfDocument.getPages().size());
        pdfDocument.finishPage(page);

        // Save the PDF file
        try {
            File pdfFile = new File(getExternalFilesDir(null), "Patient_History_" + phn + ".pdf");
            pdfDocument.writeTo(new FileOutputStream(pdfFile));

            Toast.makeText(this, "PDF saved to: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            System.out.println("PDF saved to: " + pdfFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            pdfDocument.close();
        }
    }

    private void drawFooter(Canvas canvas, Paint footerPaint, int pageWidth, int pageHeight, int pageNumber) {
        // Draw footer with page number
        String footerText = "Page " + pageNumber;
        canvas.drawText(footerText, pageWidth - 100, pageHeight - 20, footerPaint);
    }

    private void drawHeaderWithPatientDetails(Canvas canvas, Paint headerPaint, Paint linePaint, Patient patient, String phn, int pageWidth, int yPosition) {
        // Draw PHN and Patient Name on the left
        canvas.drawText("PHN: " + phn, 20, yPosition, headerPaint);
        canvas.drawText("Name: " + patient.getName(), 20, yPosition + 20, headerPaint);

        // Draw Phone and Address on the right
        canvas.drawText("Phone: " + patient.getPhone(), pageWidth - 200, yPosition, headerPaint);
        canvas.drawText("Address: " + patient.getAddress(), pageWidth - 200, yPosition + 20, headerPaint);

        // Draw a horizontal line below the header
        canvas.drawLine(10, yPosition + 40, pageWidth - 10, yPosition + 40, linePaint);
    }

    private void loadPatientHistory(String phn) {
        patientHistoryDAO = new PatientHistoryDAO(this);
        List<PatientHistory> historyList = patientHistoryDAO.getPatientHistoryByPhn(phn);

        if (!historyList.isEmpty()) {
            PatientHistoryAdapter adapter = new PatientHistoryAdapter(this, historyList);
            lvPatientHistory.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No history found for this PHN.", Toast.LENGTH_SHORT).show();
        }
    }


}