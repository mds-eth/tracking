package br.com.project.util.all;

/**
 *
 * @author michael
 */
public enum StatusPersistencia {

    ERRO("Erro"),
    SUCESSO("Sucesso"),
    OBJETO_REFERENCIADO("Este objeto não pode ser apagado pois possui referencias ao mesmo");

    private String name;

    private StatusPersistencia(String s) {

        this.name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
