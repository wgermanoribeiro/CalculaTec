package com.germanoribeiro.calculatec;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculadoraActivity extends Activity {
    private Calculadora calc;
    private boolean usuarioEstaDigitandoUmNumero;
    private boolean separadorDecimalFoiDigitado;
    private TextView txtVisor;
    private String separador;
    private char separadorChar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        calc = new Calculadora();
        usuarioEstaDigitandoUmNumero = false;
        separadorDecimalFoiDigitado = false;
        txtVisor = (TextView) findViewById(R.id.txtVisor);
        txtVisor.setText("0");
        Locale localizacao = getResources().getConfiguration().locale;
        NumberFormat formatador = NumberFormat.getInstance(localizacao);

        separadorChar = ',';
        if (formatador instanceof DecimalFormat){
            DecimalFormatSymbols simbolo = ((DecimalFormat)formatador).getDecimalFormatSymbols();
            separadorChar = simbolo.getDecimalSeparator();
        }
        separador = String.valueOf(separadorChar);
        Button btnSeparador = (Button)findViewById(R.id.buttonVirgula);
        btnSeparador.setText(separador);

        //Uso da fonte digital
        final Typeface fonteDigital = Typeface.createFromAsset(this.getAssets(), "digital-7.ttf");
        txtVisor.setTypeface(fonteDigital);

        txtVisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtVisor.getTypeface().equals(fonteDigital)){
                    txtVisor.setTypeface(Typeface.DEFAULT);
                } else {
                    txtVisor.setTypeface(fonteDigital);
                }
            }
        });
        Toast.makeText(this, "Toque no visor para alterar sua fonte", Toast.LENGTH_LONG).show();
    }
    public void onClickNumeros(View v){
        Button botaoTocado = (Button) v;
        String digito = botaoTocado.getText().toString();
        String textoNoVisor = txtVisor.getText().toString();
        if (!usuarioEstaDigitandoUmNumero || textoNoVisor.equals("0")){
            txtVisor.setText(digito);
            if (!digito.equals("0")){
                usuarioEstaDigitandoUmNumero = true;
            }
        } else {
            txtVisor.setText(textoNoVisor + digito);
        }
    }
    public void onClickOperacoes(View v){
        Button botaoTocado = (Button) v;
        String operacao = botaoTocado.getText().toString();

        if(operacao.equals(separador) && !separadorDecimalFoiDigitado){
            separadorDecimalFoiDigitado = true;
            if(!usuarioEstaDigitandoUmNumero)
                txtVisor.setText("0" + separador);
            else
                txtVisor.setText(txtVisor.getText().toString() + separador);
            usuarioEstaDigitandoUmNumero = true;
        } else if (!operacao.equals(separador)) {
            String valorSemVirgula = txtVisor.getText().toString().replace(separadorChar,'.');
            calc.setOperando(Double.parseDouble(valorSemVirgula));
            calc.realizarOperacao(operacao);
            String textoResultado = String.valueOf(calc.getOperando());
            if (textoResultado.endsWith(".0")){
                textoResultado = textoResultado.substring(0, textoResultado.length()-2);
            }
            txtVisor.setText(textoResultado.replace('.', separadorChar));
            usuarioEstaDigitandoUmNumero = false;
            separadorDecimalFoiDigitado = false;
        }
    }
    public void onClickMemoria(View v){
        Button botaoTocado = (Button) v;
        String operacaoMemoria = botaoTocado.getText().toString();
        String valorSemVirgula = txtVisor.getText().toString().replace(separadorChar,'.');
        calc.setOperando(Double.parseDouble(valorSemVirgula));
        calc.realizarOperacaoDeMemoria(operacaoMemoria);
        usuarioEstaDigitandoUmNumero = false;
    }
}
