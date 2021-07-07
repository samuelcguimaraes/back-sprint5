package br.com.rchlo.cards.domain;

import br.com.rchlo.cards.service.fraud.ExpendsAllLimit;
import br.com.rchlo.cards.service.fraud.TooFast;
import br.com.rchlo.cards.service.fraud.Verifiable;

import javax.persistence.*;

@Entity
public class FraudVerifier {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private boolean enabled;
    
    public enum Type {
        EXPENDS_ALL_LIMIT {
            @Override
            public Verifiable getVerifier() {
                return new ExpendsAllLimit();
            }
        },
        TOO_FAST {
            @Override
            public Verifiable getVerifier() {
                return new TooFast();
            }
        };
        
        public abstract Verifiable getVerifier();
    }

    public Type getType() {
        return this.type;
    }
}
