package com.example.application.views.PrestamoView.AsyncPush;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.MovimientoType;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.service.TransactionService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Push
@Route("asyncpush")
public class AsyncPush extends Div {

    private FeederThread thread;
    private BankAccount bankAccountAsync;
    private int durationAsync;
    private double interesAsync;
    private Double importePrestamo;
    private TransactionService transactionServiceAsync;



    public AsyncPush(BankAccount bankAccount, String cantidad, String duracionSelect, String tipoDeInteres, TransactionService transactionService){

        super();
        this.transactionServiceAsync = transactionService;
        this.bankAccountAsync = bankAccount;
        this.durationAsync = Integer.parseInt(duracionSelect);
        this.interesAsync = Double.parseDouble(tipoDeInteres);
        this.importePrestamo = Double.valueOf(cantidad);

        thread = new FeederThread(this.bankAccountAsync, this.importePrestamo, this.durationAsync,  this.interesAsync, this.transactionServiceAsync);
        thread.start();

    }

    @Override
    public void onAttach(AttachEvent attachEvent) {

        thread = new FeederThread(this.bankAccountAsync, this.importePrestamo, this.durationAsync,  this.interesAsync, this.transactionServiceAsync);
        thread.start();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        // Cleanup
        thread.interrupt();
        thread = null;
    }

    private static class FeederThread extends Thread {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        private TransactionService transactionServiceThread;

        private int count = 1;
        BankAccount threadBankAccount;
        private int threadDuration;
        private double threadInteres;
        private Double threadImportePrestamo;


        public FeederThread(BankAccount bankAccount, Double importePrestamoAsync, int duration, Double interes, TransactionService transactionServiceAsync) {
            this.transactionServiceThread = transactionServiceAsync;
            this.threadBankAccount  = bankAccount;
            this.threadImportePrestamo = importePrestamoAsync;
            this.threadDuration = duration;
            this.threadInteres = interes;
        }

        @Override
        public void run() {
            try {
                // Update the data for a while
                while (count <= this.threadDuration) {
                    // Sleep to emulate background work
                    Thread.sleep(5000);

                    createTransaction(count);
                    count++;


                }

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        private Boolean createTransaction(int count) throws IOException {

            TransactionDTO nuevaTransaction = new TransactionDTO();
            MovimientoType movimientoTransferencia = MovimientoType.TRANSFERENCIA;

            String message = "Cobro Prestamo numero " + count + "/" + this.threadDuration;

            //CALCULO CUOTA MENSUAL DE COBRO
            Double cuotaMensual = (this.threadImportePrestamo + ( this.threadImportePrestamo / this.threadInteres)) / threadDuration;

            try {

                nuevaTransaction.setConcepto(message);
                nuevaTransaction.setImporte(Double.valueOf(cuotaMensual));
                nuevaTransaction.setTipoMovimiento(movimientoTransferencia);
                nuevaTransaction.setIdBankAccount(this.threadBankAccount.getId());

                this.transactionServiceThread.createTransactionVaadin(nuevaTransaction);

            } catch (Exception e) {
                logger.error(e.getMessage());
                nuevaTransaction.setId(-500L);
                return false;
            }
            return true;
        }
    }
}