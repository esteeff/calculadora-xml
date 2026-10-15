package mx.unison;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class VariablesHandler extends DefaultHandler {

    private final String variableBuscada;
    private final List<Double> valores;
    private boolean dentroDeLaVariable;
    private StringBuilder textoActual;

    public VariablesHandler(String var) {
        super();
        this.variableBuscada = var;
        this.valores = new ArrayList<>();
        this.dentroDeLaVariable = false;
        this.textoActual = new StringBuilder();
    }

    public List<Double> getValores() {
        return valores;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(variableBuscada)) {
            dentroDeLaVariable = true;
            textoActual.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (dentroDeLaVariable) {
            textoActual.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(variableBuscada)) {
            String texto = textoActual.toString().trim();
            try {
                double valor = Double.parseDouble(texto);
                valores.add(valor);
            } catch (NumberFormatException e) {
                throw new SAXException("La variable '" + variableBuscada + "' no es numerica. Valor encontrado: " + texto);
            }
            dentroDeLaVariable = false;
        }
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        super.warning(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        super.fatalError(e);
    }
}