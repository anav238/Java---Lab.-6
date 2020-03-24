import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Clasa Catalog implementeaza interfata Serializabil pentru a putea fi serializat (scris in fisier)
public class Catalog implements Serializable {
    private String name;
    private String path;
    private List<Document> documents = new ArrayList<>();

    public Catalog(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void add(Document doc) {
        documents.add(doc);
    }

    public Document findById(String id) {
        //Cautam documentul in lista de documente cu ajutorul unui filter bazat pe egalitatea dintre
        //id-ul documentului cautat si id-ul celorlalte documente. Primul match este mereu
        //returnat, iar daca nu gasim niciun rezultat, returnam null.
        return documents.stream()
                .filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(name, catalog.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
