package com.example.application.views.chart;

import com.example.application.backend.model.BankAccount;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.model.User;
import com.example.application.backend.model.bankaccount.operations.BankAccountUserResponse;
import com.example.application.backend.repository.BankAccountRepository;
import com.example.application.backend.security.service.UserDetailsServiceImpl;
import com.example.application.backend.service.BankAccountService;
import com.example.application.backend.service.CategoryService;
import com.example.application.backend.service.TransactionOperationsService;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;

import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;

import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import java.util.ArrayList;
import java.util.List;

@Route(value = "balance", layout = MainView.class)
@PageTitle("Balance")
public class ChartView extends HorizontalLayout {

	private UserDetailsServiceImpl userDetailsService;

	private static User userLogged;

	private Transaction transactionTest = new Transaction();
	private TransactionService transactionService;
	private TransactionOperationsService transactionOperationsService;
	private CategoryService categoryService;
	private BankAccountService bankAccountService;

	Series testSeries = new Series();


	public ChartView(TransactionService transactionService, CategoryService categoryService, TransactionOperationsService transactionOperationsService,
					 UserDetailsServiceImpl userDetailsService, BankAccountService bankAccountService){
		this.transactionService = transactionService;
		this.categoryService = categoryService;
		this.transactionOperationsService = transactionOperationsService;
		this.userDetailsService = userDetailsService;
		this.bankAccountService = bankAccountService;

		this.setSizeFull();
		this.setPadding(true);

		this.userLogged = userDetailsService.getUserLogged();


		add(new AreaBarChartExample(this.userLogged), new DonutChartExample(this.userLogged));

	}

	public class AreaBarChartExample extends Div {
		public AreaBarChartExample(User userLogged) {

			Object transactionTest = transactionService.findAllBalanceAfterTransaction(userLogged.getId());
			Series testserie = new Series();
			testserie.setData((Object[]) transactionTest);


					ApexCharts barChart = ApexChartsBuilder.get()
							.withChart(ChartBuilder.get()
									.withType(Type.bar)
									.build())
							.withPlotOptions(PlotOptionsBuilder.get()
									.withBar(BarBuilder.get()
											.withHorizontal(true)
											.build())
									.build())
							.withDataLabels(DataLabelsBuilder.get()
									.withEnabled(false)
									.build())
							.withSeries(new Series<>("balance after transaction",testserie.getData()))
							.withXaxis(XAxisBuilder.get()
									.withCategories()
									.build())
							.build();
					add(barChart);
					setWidth("50%");
				}
	}


			public class DonutChartExample extends Div {
				public DonutChartExample(User userLogged)
				{
					BankAccountUserResponse bankAccountUserResponse = bankAccountService.findAllBankAccountsByIdUser(userLogged.getId());


					List transactionOperations = transactionOperationsService.getAllOperationsByCategoryBankAccount(bankAccountUserResponse.getBankAccounts().get(0).getId());
					List<String> categoriesName = categoryService.findChartCategoriesAllByName();

					Series donutSerie = new Series();

					List<String> listaString= new ArrayList<>();
					List<Double> listaDouble = new ArrayList<>();

					for(int x = 0; x < transactionOperations.size();x++) {
						listaString.add(transactionOperations.get(x).toString());
						listaDouble.add(Double.valueOf(listaString.get(x)));
					}

					ApexCharts donutChart = ApexChartsBuilder.get()
							.withChart(ChartBuilder.get().withType(Type.donut).build())
							.withLegend(LegendBuilder.get()
									.withPosition(Position.right)
									.build())

							.withSeries(listaDouble.get(0), listaDouble.get(1), listaDouble.get(2), listaDouble.get(3), listaDouble.get(4))
							.withLabels(categoriesName.get(0), categoriesName.get(1), categoriesName.get(2), categoriesName.get(3), categoriesName.get(4))

							.withResponsive(ResponsiveBuilder.get()
									.withBreakpoint(480.0)
									.withOptions(OptionsBuilder.get()
											.withLegend(LegendBuilder.get()
													.withPosition(Position.bottom)
													.build())
											.build())
									.build())
							.build();
					add(donutChart);
					setWidth("50%");
				}
			}

}

