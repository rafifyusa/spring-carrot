package com.mitrais.jpqi.springcarrot.service;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mitrais.jpqi.springcarrot.model.Sequence;
import com.mitrais.jpqi.springcarrot.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceService {
    @Autowired
    private MongoOperations mongoOperations;

//    public int getNextSequence(String seqName)
//    {
//        Sequence counter = mongo.findAndModify(
//                query(where("_id").is(seqName)),
//                new Update().inc("seq",1),
//                options().returnNew(true).upsert(true),
//                Sequence.class);
//        return counter.getSeq();
//    }

    public int generateSequence(String seqName) {
        Sequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                Sequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
