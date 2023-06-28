package hexlet.code.model.query.assoc;

import hexlet.code.model.Url;
import hexlet.code.model.query.QUrl;
import io.ebean.Transaction;
import io.ebean.typequery.Generated;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PLong;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQAssocBean;
import io.ebean.typequery.TQProperty;
import io.ebean.typequery.TypeQueryBean;

/**
 * Association query bean for AssocUrl.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@Generated("io.ebean.querybean.generator")
@TypeQueryBean("v1")
public final class QAssocUrl<R> extends TQAssocBean<Url,R,QUrl> {

  public PLong<R> id;
  public PString<R> name;
  public PInstant<R> createdAt;

  public QAssocUrl(String name, R root) {
    super(name, root);
  }

  public QAssocUrl(String name, R root, String prefix) {
    super(name, root, prefix);
  }
}
