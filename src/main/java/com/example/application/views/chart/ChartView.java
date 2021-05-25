package com.example.application.views.chart;

import com.example.application.views.main.MainView;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class ChartView extends HorizontalLayout {

	public ChartView(){
		this.setSizeFull();
		this.setPadding(true);

		// add two rows
		add(new AreaChartExample(), new DonutChartExample());

	}


	public class AreaChartExample extends Div {
		public AreaChartExample() {
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
					.withSeries(new Series<>("STOCK ABC", 10.0, 41.0, 35.0, 51.0, 49.0, 62.0, 69.0, 91.0, 148.0))
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
			setWidth("100%");
		}
	}


			public class DonutChartExample extends Div {
				public DonutChartExample() {
					ApexCharts donutChart = ApexChartsBuilder.get()
							.withChart(ChartBuilder.get().withType(Type.donut).build())
							.withLegend(LegendBuilder.get()
									.withPosition(Position.right)
									.build())
							.withSeries(44.0, 55.0, 41.0, 17.0, 15.0)
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
					setWidth("100%");
				}
			}


}

