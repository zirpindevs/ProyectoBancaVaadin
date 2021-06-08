package com.example.application.views.PrestamoView.AsyncPush;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.Category;
import com.example.application.backend.model.MovimientoType;
import com.example.application.backend.model.TransactionDTO;
import com.example.application.backend.service.CategoryService;
import com.example.application.backend.service.TransactionService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Push
@Route("asyncpush")
public class AsyncPush extends Div {

    private static final int WAITING_TIME = 5000;

    private FeederThread thread;
    private static BankAccount bankAccountAsync = new BankAccount();
    private static int durationAsync = 0;
    private static double loanRate = 0;
    private static Double loanedMoney = null;
    private static TransactionService transactionServiceAsync = null;



    public AsyncPush(BankAccount bankAccount, String cantidad, String duracionSelect, String tipoDeInteres, TransactionService transactionService){

        super();
        this.transactionServiceAsync = transactionService;
        this.bankAccountAsync = bankAccount;
        this.durationAsync = Integer.parseInt(duracionSelect);
        this.loanRate = Double.parseDouble(tipoDeInteres);
        this.loanedMoney = Double.valueOf(cantidad);

        thread = new FeederThread();
        thread.start();
    }



    private static class FeederThread extends Thread {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        private int count = 1;
        
        public FeederThread() {
        }

        /**
         * It runs the main loop that holds the thread for A WAITING TIME between create
         * the call createtransaction() function
         * Counting each iteration , making the account of monthly quota to pay,
         * insert and updating in the database tables
         *
         */
        @Override
        public void run() {
            try {
                // Update the data for a while
                while (count <= durationAsync) {
                    // Sleep to emulate background work
                    Thread.sleep(WAITING_TIME);

                    createTransaction(count);
                    count++;


                }

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Create the Loan Transaction
         * Counting each iteration , making the account of monthly quota to pay,
         * insert and updating in the database tables
         *
         * @param count number of the transaction iteration  (int count)
         * @return Boolean if the operation makes succesfull
         */
        private Boolean createTransaction(int count) throws IOException {

            TransactionDTO nuevaTransaction = new TransactionDTO();
            MovimientoType movimientoTransferencia = MovimientoType.TRANSFERENCIA;
            Long categoryOtros = 5L;
            String message = "Cobro Cuota numero " + count + "/" + durationAsync;
            logger.info(message);


            //CALCULATE MONTHLY QUOTA TO PAY
            Double monthlyQuota = (loanedMoney + ( loanedMoney / loanRate)) / durationAsync;

            try {

                nuevaTransaction.setConcepto(message);
                nuevaTransaction.setImporte(Double.valueOf(monthlyQuota));
                nuevaTransaction.setTipoMovimiento(movimientoTransferencia);
                nuevaTransaction.setIdCategory(categoryOtros);
                nuevaTransaction.setIdBankAccount(bankAccountAsync.getId());

                transactionServiceAsync.createTransactionVaadin(nuevaTransaction);

            } catch (Exception e) {
                logger.error(e.getMessage());
                nuevaTransaction.setId(-500L);
                return false;
            }
            return true;
        }
    }
}