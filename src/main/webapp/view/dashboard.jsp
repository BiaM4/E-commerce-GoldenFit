<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/serial.js"></script>
<script src="https://www.amcharts.com/lib/3/pie.js"></script>
<script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
<%@ page import="java.util.List, br.com.fatec.goldenfit.dto.GraficoVendasDTO" %>
<!DOCTYPE html>
<html lang="pt-br">
<c:import url="template-head-admin.jsp" />
<body>
<c:import url="template-header-admin.jsp" />
<c:url value="/controlador" var="stub"/>
<fmt:setLocale value = "pt_BR"/>
<%
	String dadosGrafico = (String) request.getSession().getAttribute("dadosGrafico");
	List<GraficoVendasDTO> listaDadosPeriodo = (List<GraficoVendasDTO>) request.getSession().getAttribute("listaDto");
%>
</body>
<div class="container">
	<h1 style="color:#173B21;text-align: center; font-size: 30px; margin-top: 7%; margin-bottom:3%">DASHBOARD DE VENDAS POR PRODUTO</h1>
	<div style="background-color: #173B21; height: 5px; margin-top: 2%"></div>
	<div class="card-body">
		<div class="row" style="margin-left: 10%">
			<div class="col-4">
				<p class="h5">Data inicial</p>
			</div>
			<div class="col-4">
				<p class="h5">Data final</p>
			</div>
			<div class="col-2">
				<p class="h5" style="color: #FFF;"></p>
			</div>
		</div>
		<form id="formConsultaDashboard" action="${stub}" method="post" onsubmit="return onSubmitForm();"
			  novalidate>
			<div class="row mb-2 d-flex align-items-center" style="margin-left: 10%">
				<div class="col-4">
					<input class="form-control" type="date" id="dataInicial"
						   name="dataInicial" required="true" />
				</div>
				<div class="col-4">
					<input class="form-control" type="date" id="dataFinal"
						   name="dataFinal" required="true"/>
				</div>
				<input type="hidden" name="acao" value="consultar" />
				<input type="hidden" name="viewHelper" value="ConsultarGraficoVendasProdutoVH" />
				<div class="col-2">
					<button type="submit" class="btn btn-secondary w-100 text-white" alt="Buscar"
							title="Buscar"
							style="background-color: #173B21; border-color: #173B21">
						Buscar
					</button>
				</div>
			</div>
		</form>
	</div>
</div>

<div id="lineChart" class="<%=dadosGrafico == null || dadosGrafico.isEmpty()
	? "d-none"  : ""%> mt-5 mb-5" style="height: 300px;"></div>

<%if(listaDadosPeriodo != null && !listaDadosPeriodo.isEmpty()){%>
<div class="ms-4" style="width: 97%;">
	<h3 style="color:#173B21;text-align: center; margin-top: 2%; margin-bottom:2%">Relatório de Vendas</h3>
	<div class="row">
		<div>
			<div class="col-12 mb-3">
				<div class="row d-flex justify-content-between" style="margin-left: 5%; margin-right: 5%">
					<table class="table">
						<thead class="table table-striped">
						<tr>
							<th>Período</th>
							<th>Quantidade Vendida</th>
							<th>Valor Total de Vendas</th>
						</tr>
						</thead>
						<tbody>
						<%
							for (GraficoVendasDTO dto : listaDadosPeriodo) {
						%>
						<tr>
							<td><%=dto.getPeriodo()%></td>
							<td><fmt:formatNumber value = "<%=dto.getQuantidadeTotalVendasPeriodo()%>" type = "number" maxFractionDigits="0"/></td>
							<td><fmt:formatNumber value = "<%=dto.getValorTotalVendasPeriodo()%>" type = "currency"/></td>
								<%
								}
							%>
						</tbody>
					</table>
					<br />
				</div>
			</div>
		</div>
	</div>
</div>
<% } else { %>
<div class="container">
	<h3 style="color: #173B21; text-align: center; margin-top: -26%">Nenhum dado disponível para o período selecionado.</h3>
</div>
<% } %>

<script>
	function setCookie(name, value, days) {
		var expires = "";
		if (days) {
			var date = new Date();
			date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
			expires = "; expires=" + date.toUTCString();
		}
		document.cookie = name + "=" + (value || "") + expires + "; path=/";
	}

	function getCookie(name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for(var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) === ' ') c = c.substring(1, c.length);
			if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
		}
		return null;
	}

	function onSubmitForm() {
		setCookie('dataInicial', document.getElementById('dataInicial').value, 30);
		setCookie('dataFinal', document.getElementById('dataFinal').value, 30);

		return true;
	}

	const dados = <%=dadosGrafico%>;

	function addVerticalJitter(value) {
		const jitter = (Math.random() - 0.5) * 0.4;
		return value + jitter;
	}

	if (typeof dados != "undefined") {
		dados.forEach(dado => {
			for (let key in dado) {
				if (key !== "category" && typeof dado[key] === 'number') {
					dado[key] = parseFloat(addVerticalJitter(dado[key]).toFixed(2));
				}
			}
		});

		let chart = AmCharts.makeChart("lineChart", {
			"type": "serial",
			"categoryField": "category",
			"startDuration": 0,
			"dataProvider": dados,
			"theme": "light",
			"categoryAxis": {
				"gridPosition": "start"
			},
			"export": {
				"enabled": true
			},
			"trendLines": [],
			"graphs": [],
			"guides": [],
			"valueAxes": [
				{
					"id": "ValueAxis-1",
					"title": "Unidades vendidas"
				}
			],
			"allLabels": [],
			"balloon": {
				"fixedPosition": true,
				"adjustBorderColor": true,
				"color": "#000000",
				"cornerRadius": 5,
				"fillColor": "#FFFFFF",
				"horizontalPadding": 10,
				"verticalPadding": 10
			},
			"legend": {
				"enabled": true,
				"useGraphSettings": true
			},
			"chartCursor": {
				"categoryBalloonDateFormat": "MMM YYYY",
				"cursorPosition": "mouse",
				"oneBalloonOnly": true
			},
			"titles": [
				{
					"id": "Title-1",
					"size": 17,
					"text": "Volume de Vendas no Período por Produto"
				}
			]
		});

		let campos = [];
		chart.dataProvider.forEach(dado => campos.push(Object.keys(dado).filter(function (value) {
			return value !== chart.categoryField;
		})));

		campos = campos.join().split(",");

		let valorCampos = campos.filter(function (valor, i) {
			return campos.indexOf(valor) === i;
		});

		valorCampos.forEach(function (valueField) {
			chart.graphs.push({
				"valueField": valueField,
				"title": valueField,
				"type": "line",
				"bullet": "round",
				"bulletSize": 10,
				"bulletBorderThickness": 2,
				"bulletBorderColor": "#FFFFFF",
				"balloonText": "[[title]]: [[value]] vendas",
				"lineThickness": 2,
				"lineAlpha": 0.7,
				"balloonFunction": function(item, graph) {
					return graph.title + ": " + Math.round(item.values.value) + " vendas";
				}
			});
		});

		chart.validateData();
	}

	window.onload = function() {
		let dataInicial = getCookie('dataInicial');
		let dataFinal = getCookie('dataFinal');

		if (dataInicial) {
			document.getElementById('dataInicial').value = dataInicial;
		}
		if (dataFinal) {
			document.getElementById('dataFinal').value = dataFinal;
		}
	}
</script>
</html>