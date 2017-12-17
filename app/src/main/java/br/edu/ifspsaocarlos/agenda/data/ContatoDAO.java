package br.edu.ifspsaocarlos.agenda.data;


import java.util.List;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class ContatoDAO{

    private RealmResults<Contato> mContato;
    private RealmHelper database;
    //private RealmResults<Contato> database;

    public ContatoDAO() {
        Realm.getDefaultInstance();
    }

    public  List<Contato> buscaTodosContatos(){
        Realm database = Realm.getDefaultInstance();
        RealmQuery<Contato> query = database.where(Contato.class);
        RealmResults<Contato> result = query.findAll().sort("id").sort("nome");
        //result.sort("nome", Sort.ASCENDING);
        List<Contato> contatos;
        contatos = result;
        database.close();
        return contatos;
    }


    public  List<Contato> buscaContato(String nome) {
        Realm database = Realm.getDefaultInstance();
        List<Contato> contatos;
        if (nome == "FILTRO_FAVORITO" || nome == "FILTRA_NAO_FAVORITOS") {
            if (nome == "FILTRO_FAVORITO") {
                RealmQuery<Contato> query = database.where(Contato.class)
                        .equalTo("isfavorito", true);
                RealmResults<Contato> contato = query.findAll().sort("nome");
                //contato.sort("nome");
                contatos = contato;
            }
            else {
                RealmQuery<Contato> query = database.where(Contato.class)
                        .equalTo("isfavorito", false);
                RealmResults<Contato> contato = query.findAll().sort("nome");
                //contato.sort("nome");
                contatos = contato;
            }
        }
        else {

            RealmQuery<Contato> query = database.where(Contato.class)
                    .contains("nome", nome)
                    .or()
                    .contains("email", nome);
            RealmResults<Contato> contato = query.findAll().sort("nome");
            //contato.sort("nome");

            contatos = contato;
        }
        database.close();
        return contatos;

    }

    public void salvaContato(final Contato c) {

        if (c.getId()>0) {
            Realm database = Realm.getDefaultInstance();
            final Contato alterar = database.where(Contato.class)
                    .equalTo("id", c.getId()).findFirst();
            if (alterar != null)

            {

                database.executeTransaction(new Realm.Transaction() {

                    @Override

                    public void execute(Realm realm) {

                        //c.setNome(“SC”);

                        alterar.setNome(c.getNome());
                        alterar.setFone(c.getFone());
                        alterar.setFone2(c.getFone2());
                        alterar.setEmail(c.getEmail());
                        alterar.setAniver(c.getAniver());
                        alterar.setFavorito(c.getFavorito());

                    }

                });

            }
        }
        else {
            Realm database = Realm.getDefaultInstance();
            database.beginTransaction();

            /**
             * incrementa o campo do id
             */

            Number currentIdNum = database.where(Contato.class).max("id");
            int nextId;
            if (currentIdNum == null) {
                nextId = 1;

            }
            else {
                nextId = currentIdNum.intValue() + 1;
            }
            Contato contato = database.createObject(Contato.class, nextId);
            //contato.setId(nextId);
            contato.setNome(c.getNome());
            contato.setFone(c.getFone());
            contato.setFone2(c.getFone2());
            contato.setEmail(c.getEmail());
            contato.setAniver(c.getAniver());
            contato.setFavorito(c.getFavorito());
            database.commitTransaction();
            database.close();
        }
    }



    public void apagaContato(Contato c) {
        Realm database = Realm.getDefaultInstance();
        // Realm database;

        final Contato apagar = database.where(Contato.class)
                .equalTo("id", c.getId()).findFirst();

        if (apagar != null) {

            database.executeTransaction(new Realm.Transaction() {

                @Override

                public void execute(Realm realm) {

                    apagar.deleteFromRealm();

                }

            });

        }
        {

        }
    }
}
