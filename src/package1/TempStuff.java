package package1;
 
import java.io.IOException;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
 
public class TempStuff {
    public static Analyzer analyzer = new StandardAnalyzer();
    public static IndexWriterConfig config = new IndexWriterConfig(analyzer);
    public static RAMDirectory ramDirectory = new RAMDirectory();
    public static IndexWriter indexWriter;
 
    public static void main(String args []) throws ParseException {
        createIndex();
        searchSingleTerm("title","lucene");
        ramDirectory.close();
    }
 
    public static void createDoc(String author, String title, String date) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("author", author, Field.Store.YES));
        doc.add(new TextField("title", title, Field.Store.YES));
        //doc.add(new Field ("date", date, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new SortedDocValuesField ("date", new BytesRef(date) ));
        doc.add(new StoredField("date", date));
 
        indexWriter.addDocument(doc);
    }
 
    public static void createIndex() {
        try {
                indexWriter = new IndexWriter(ramDirectory, config);    
                createDoc("Sam", "Lucece index option analyzed vs not analyzed", "2016-12-12 20:19:57");    
                createDoc("Sam", "Lucene field boost and query time boost example", "2016-03-16 16:57:44");    
                createDoc("Jack", "How to do Lucene search highlight example", "2016-03-16 17:47:38");
                createDoc("Smith","Lucene BooleanQuery is depreacted as of 5.3.0" , "2015-04-30 11:44:25");
                createDoc("Smith","What is term vector in Lucene", "2015-04-10 20:33:53" );
                createDoc("Smith2","2What is term vector in Lucene", "2015-05-10 20:33:53" );
                createDoc("Smith3","3What is term vector in Lucene", "2015-06-10 20:33:53" );
                
                indexWriter.close();
        } catch (IOException | NullPointerException ex) {
            System.out.println("Exception : " + ex.getLocalizedMessage());
        } 
    }
 
 
     public static void searchIndexNoSortAndDisplayResults(Query query) {
         try {
             IndexReader idxReader = DirectoryReader.open(ramDirectory);
             IndexSearcher idxSearcher = new IndexSearcher(idxReader);
 
             TopDocs docs = idxSearcher.search(query, 100);
             System.out.println("length of top docs: " + docs.scoreDocs.length);
             for (ScoreDoc doc : docs.scoreDocs) {
                 Document thisDoc = idxSearcher.doc(doc.doc);
                 System.out.println(doc.doc + "\t" + thisDoc.get("author")
                         + "\t" + thisDoc.get("title"));
             }
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
         }
     }
 
    public static void searchIndexAndDisplayResults(Query query) {
        try {
            IndexReader idxReader = DirectoryReader.open(ramDirectory);
            IndexSearcher idxSearcher = new IndexSearcher(idxReader);
 
            Sort sort = new Sort(SortField.FIELD_SCORE,
                    new SortField("date", Type.STRING));
 
            TopDocs docs = idxSearcher.search(query, 10, sort,true, true);
            System.out.println("length of top docs: " + docs.scoreDocs.length + " sort by: " + sort);
            for (ScoreDoc doc : docs.scoreDocs) {
                Document thisDoc = idxSearcher.doc(doc.doc);
                System.out.println(doc.doc + "\t" + thisDoc.get("author")
                        + "\t" + thisDoc.get("title")
                        + "\t" + thisDoc.get("date"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
 
    public static void searchSingleTerm(String field, String termText){
        Term term = new Term(field, termText);
        TermQuery termQuery = new TermQuery(term);
 
        searchIndexAndDisplayResults(termQuery);
        searchIndexNoSortAndDisplayResults(termQuery);
    }
 
 }
 
 