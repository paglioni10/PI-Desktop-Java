package Controller.Helper;

/**
 * Contrato que todo Helper do projeto deve seguir: saber montar o modelo
 * a partir dos campos da tela e limpar esses campos.
 */
public interface IHelper {

    public Object obterModelo();

    public void limparTela();
}
