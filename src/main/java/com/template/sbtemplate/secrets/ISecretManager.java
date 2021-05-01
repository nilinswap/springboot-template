package com.template.sbtemplate.secrets;

import com.template.sbtemplate.pojo.DBCredentials;

public interface ISecretManager {

    DBCredentials getDBCredentials(String dbname);
}
