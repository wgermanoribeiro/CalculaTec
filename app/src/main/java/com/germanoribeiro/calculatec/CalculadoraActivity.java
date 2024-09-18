package com.germanoribeiro.calculatec;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class CalculadoraActivity extends Activity {
    private Calculadora calc;
    private boolean usuarioEstaDigitandoUmNumero;
    private boolean separadorDecimalFoiDigitado;
    private TextView txtVisor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        calc = new Calculadora();
        usuarioEstaDigitandoUmNumero = false;
        separadorDecimalFoiDigitado = false;
        txtVisor = (TextView) findViewById(R.id.txtVisor);
        txtVisor.setText("0");
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
        if(operacao.equals(",") && !separadorDecimalFoiDigitado){
            separadorDecimalFoiDigitado = true;
            if(!usuarioEstaDigitandoUmNumero)
                txtVisor.setText("0" +",");
            else
                txtVisor.setText(txtVisor.getText().toString()+",");
            usuarioEstaDigitandoUmNumero = true;
        } else if (!operacao.equals(",")) {
            String valorSemVirgula = txtVisor.getText().toString().replace(',','.');
            calc.setOperando(Double.parseDouble(valorSemVirgula));
            calc.realizarOperacao(operacao);
            String textoResultado = String.valueOf(calc.getOperando());
            if (textoResultado.endsWith(".0")){
                textoResultado = textoResultado.substring(0, textoResultado.length()-2);
            }
            txtVisor.setText(textoResultado.replace('.',','));
            usuarioEstaDigitandoUmNumero = false;
            separadorDecimalFoiDigitado = false;
        }
    }
    public void onClickMemoria(View v){
        Button botaoTocado = (Button) v;
        String operacaoMemoria = botaoTocado.getText().toString();
        String valorSemVirgula = txtVisor.getText().toString().replace(',','.');
        calc.setOperando(Double.parseDouble(valorSemVirgula));
        calc.realizarOperacaoDeMemoria(operacaoMemoria);
        usuarioEstaDigitandoUmNumero = false;
    }
}
