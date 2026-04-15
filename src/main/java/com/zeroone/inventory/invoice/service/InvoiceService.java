package com.zeroone.inventory.invoice.service;

import com.zeroone.inventory.invoice.entity.Invoice;
import com.zeroone.inventory.invoice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public String generateInvoiceNumber() {

        Invoice lastInvoice = invoiceRepository.findTopByOrderByIdDesc();

        if (lastInvoice == null) {
            return "INV-1001";
        }

        String lastNumber = lastInvoice.getInvoiceNumber();

        int number = Integer.parseInt(lastNumber.split("-")[1]);

        return "INV-" + (number + 1);
    }

    public Invoice createInvoice(Invoice invoice) {

        invoice.setInvoiceNumber(generateInvoiceNumber());

        invoice.setInvoiceDate(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }

    public byte[] generateInvoicePdf(Long invoiceId) {

        Invoice invoice = getInvoiceById(invoiceId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Invoice"));
            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
            document.add(new Paragraph("Date: " + invoice.getInvoiceDate()));
            document.add(new Paragraph("Purchase ID: " + invoice.getPurchaseId()));
            document.add(new Paragraph("Total Amount: " + invoice.getTotalAmount()));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF");
        }

        return out.toByteArray();
    }

    public List<Invoice> getInvoicesByPurchaseId(Long purchaseId) {
        return invoiceRepository.findByPurchaseId(purchaseId);
    }
}