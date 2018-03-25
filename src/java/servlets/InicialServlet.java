package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "InicialServlet", urlPatterns = {"/index.html"})
public class InicialServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        double juros, valor;
        int meses;
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("valor") != null && !"".equals(request.getParameter("valor"))) {
                valor = Double.parseDouble(request.getParameter("valor"));
            } else {
                valor = 5000;
            }
            if (request.getParameter("juros") != null && !"".equals(request.getParameter("juros"))) {
                juros = Double.parseDouble(request.getParameter("juros"));
            } else {
                juros = 1;
            }

            if (request.getParameter("meses") != null && !"".equals(request.getParameter("meses"))) {
                meses = Integer.parseInt(request.getParameter("meses"));
            } else {
                meses = 12;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Juros Compostos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Juros Compostos: Análise de Negócio</h1>");
            out.println("<form>");
            out.println("<label>Valor do Investimento: </label><input type=\"text\" name=\"valor\"><br><br>");
            out.println("<label>Valor do Juros: </label><input type=\"text\" name=\"juros\"><br><br>");
            out.println("<label>Número de Meses: </label><input type=\"text\" name=\"meses\"><br><br>");
            out.println("<button type=\"submit\">Calcular</button>");
            out.println("</form>");
            out.println("<h4>" + constroiMensagem(valor, juros, meses) + "</h4>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String constroiMensagem(Double valor, Double juros, int numeroMeses) {
        StringBuilder mensagem = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,###.00");
        Double valorFinal = calculaValor(valor, numeroMeses, juros / 100);
        Double rendimento = (valorFinal - valor) / numeroMeses;
        if (rendimento > 200) {
            mensagem.append("<h3 style='color:Green'>Você fez um Bom negócio! O rendimento mensal é de: ").append(df.format(rendimento)).append("</h3><br>");
        } else {
            mensagem.append("<h3 style='color:Red'>Você fez um Mau negócio!O rendimento mensal é de: ").append(df.format(rendimento)).append("</h3><br>");
        }
        mensagem.append("<p> Para um investimento inicial de R$ ").append(valor).append(" a uma taxa de juros compostos de ").append(juros).append("% ao mês, você terá R$ ").append(df.format(valorFinal)).append(" ao final de ").append(numeroMeses).append(" meses! </p>");
        return mensagem.toString();

    }

    private Double calculaValor(double valor, int meses, double juros) {

        return valor * (Math.pow(1 + juros, meses));
    }

}
