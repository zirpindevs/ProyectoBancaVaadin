package com.example.application.views.chart;

import com.example.application.backend.dao.CategoryDao;
import com.example.application.backend.dao.TransactionDAO;
import com.example.application.backend.dao.TransactionOperationsDao;
import com.example.application.backend.model.Transaction;
import com.example.application.backend.repository.CategoryRepository;
import com.example.application.backend.service.TransactionService;
import com.example.application.views.main.MainView;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.DataLabels;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.Labels;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class ChartView extends HorizontalLayout {

	Series testSeries = new Series();
	Transaction transactionTest = new Transaction();
	TransactionService transactionService;
	TransactionDAO transactionDAO;
	TransactionOperationsDao transactionOperationsDao;
	CategoryDao categoryDao;

	public ChartView(TransactionService transactionService, TransactionDAO transactionDAO, TransactionOperationsDao transactionOperationsDao, CategoryDao categoryDao){
		this.transactionService = transactionService;
		this.transactionDAO = transactionDAO;
		this.transactionOperationsDao = transactionOperationsDao;
		this.categoryDao = categoryDao;
		this.setSizeFull();
		this.setPadding(true);




		// add two rows
/*
		add(new AreaChartExample(), new DonutChartExample());
*/
		add(new AreaBarChartExample(), new DonutChartExample());


	}


	public class AreaChartExample extends Div {
		public AreaChartExample() {
			Object transactionTest = transactionDAO.findAllBalanceAfterTransaction(1L);
			Series testserie = new Series();
			testserie.setData((Object[]) transactionTest);

			ApexCharts areaChart = ApexChartsBuilder.get()
					.withChart(ChartBuilder.get()
							.withType(Type.area)
							.withZoom(ZoomBuilder.get()
									.withEnabled(false)
									.build())
							.build())
					.withDataLabels(DataLabelsBuilder.get()
							.withEnabled(false)
							.build())
					.withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
					.withSeries(new Series<>("bankaccount", testserie.getData()))
					.withTitle(TitleSubtitleBuilder.get()
							.withText("Fundamental Analysis of Stocks")
							.withAlign(Align.left).build())
					.withSubtitle(TitleSubtitleBuilder.get()
							.withText("Price Movements")
							.withAlign(Align.left).build())
					.withLabels(IntStream.range(1, 10).boxed().map(day -> LocalDate.of(2000, 1, day).toString()).toArray(String[]::new))
					.withXaxis(XAxisBuilder.get()
							.withType(XAxisType.datetime).build())
					.withYaxis(YAxisBuilder.get()
							.withOpposite(true).build())
					.withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build())
					.build();
			add(areaChart);
			setWidth("50%");
		}
	}



	public class AreaBarChartExample extends Div {
		public AreaBarChartExample() {

			Object transactionTest = transactionDAO.findAllBalanceAfterTransaction(1L);
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
				public DonutChartExample()
				{

					List transactionOperations = transactionOperationsDao.getAllOperationsByCategoryBankAccount(1L);
					List<String> categoriesName = categoryDao.findAllByName();

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

/*
	Chart chart = new Chart();

	Configuration configuration = chart.getConfiguration();
					configuration.getChart().setType(ChartType.LINE);

					configuration.getTitle()
							.setText("CALLOUT: Monthly Average Temperature");
					configuration.getSubTitle().setText("Source: WorldClimate.com");

					configuration.getxAxis().setCategories("Jan", "Feb", "Mar", "Apr",
																   "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

	YAxis yAxis = configuration.getyAxis();
					yAxis.setTitle(new AxisTitle("Temperature (°C)"));

					configuration
							.getTooltip()
							.setFormatter(
									"'<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C'");

	PlotOptionsLine plotOptions = new PlotOptionsLine();
					plotOptions.setEnableMouseTracking(false);
					configuration.setPlotOptions(plotOptions);

	DataSeries ds = new DataSeries();
					ds.setName("Tokyo");
					ds.setData(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
							13.9, 9.6);
	com.vaadin.flow.component.charts.model.DataLabels callout = new DataLabels(true);
					callout.setShape(Shape.CALLOUT);
					callout.setY(-12);
					ds.get(5).setDataLabels(callout);
					configuration.addSeries(ds);

	ds = new DataSeries();
					ds.setName("London");
					ds.setData(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6,
							4.8);

					configuration.addSeries(ds);

	add(chart);*/

}

