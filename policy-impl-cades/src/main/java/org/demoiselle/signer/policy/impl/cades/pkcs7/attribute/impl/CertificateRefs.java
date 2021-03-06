/*
 * Demoiselle Framework
 * Copyright (C) 2016 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 *
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 *
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 *
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package org.demoiselle.signer.policy.impl.cades.pkcs7.attribute.impl;

import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.bouncycastle.asn1.cms.Attribute;
import org.demoiselle.signer.core.util.MessagesBundle;
import org.demoiselle.signer.policy.engine.asn1.etsi.SignaturePolicy;
import org.demoiselle.signer.policy.impl.cades.SignerException;
import org.demoiselle.signer.policy.impl.cades.pkcs7.attribute.UnsignedAttribute;

/**
 * Complete Certificate Refs Attribute Definition
 * 
 * The Complete Certificate Refs attribute is an unsigned attribute.  
 * It references the full set of CA certificates that have been used to
 * validate a ES with Complete validation data (ES-C) up to (but not
 * including) the signer's certificate.  Only a single instance of this
 * attribute must occur with an electronic signature.
 * 
 * Note: The signer's certified is referenced in the signing certificate
 * attribute (see clause 3.1 https://www.ietf.org/rfc/rfc3126.txt)
 * 
 *  id-aa-ets-certificateRefs OBJECT IDENTIFIER ::= { iso(1) member-body(2)
 *  	  us(840) rsadsi(113549) pkcs(1) pkcs-9(9) smime(16) id-aa(2) 21}
 *  
 *  The complete certificate refs attribute value has the ASN.1 syntax  CompleteCertificateRefs.
 *  
 *  CompleteCertificateRefs ::=  SEQUENCE OF OTHERCertID
 *  
 *  OTHERCertID is defined in clause 3.8.2.
 *  
 *  The IssuerSerial that must be present in OTHERCertID.  
 *  The certHash  must match the hash of the certificate referenced.
 * 
 */
public class CertificateRefs implements UnsignedAttribute {

    private final String identifier = "1.2.840.113549.1.9.16.2.21";
    private static MessagesBundle cadesMessagesBundle = new MessagesBundle();

    @Override
    public void initialize(PrivateKey privateKey, Certificate[] certificates, byte[] content, SignaturePolicy signaturePolicy, byte[] hash) {

    }

    @Override
    public String getOID() {
        return identifier;
    }

    @Override
    public Attribute getValue() throws SignerException {
        throw new UnsupportedOperationException(cadesMessagesBundle.getString("error.not.supported",getClass().getName()));
    }

}
