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
package org.demoiselle.signer.core.extension;

import org.demoiselle.signer.core.oid.OIDGeneric;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_1;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_2;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_3;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_4;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_5;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_6;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_7;
import org.demoiselle.signer.core.oid.OID_2_16_76_1_3_8;
import org.demoiselle.signer.core.util.MessagesBundle;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Certificate Extra <br>
 * <br>
 *
 * Extra Informations for ICP-BRASIL (DOC-ICP-04) Certificates. Abstracts the
 * rules to "PESSOA FISICA", "PESSOA JURIDICA" and "EQUIPAMENTO/APLICAÇÃO"
 *
 */
public class CertificateExtra {

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;

    private String email = null;
    private final Map<String, OIDGeneric> extras = new HashMap<>();
    private static MessagesBundle coreMessagesBundle = new MessagesBundle();

    /**
     *
     * @param certificate The certificate to be analyzed
     */
    public CertificateExtra(X509Certificate certificate) {
        try {
            if (certificate.getSubjectAlternativeNames() == null) {
                return;
            }
            for (List<?> list : certificate.getSubjectAlternativeNames()) {
                if (list.size() != 2) {
                    throw new Exception(coreMessagesBundle.getString("error.extra.size.incorret"));
                }

                Object e1, e2;

                e1 = list.get(0);
                e2 = list.get(1);

                if (!(e1 instanceof Integer)) {
                    throw new Exception(coreMessagesBundle.getString("error.type.not.integer"));
                }

                Integer tipo = (Integer) e1;

                if (tipo.equals(ZERO)) {
                    byte[] data = (byte[]) e2;
                    OIDGeneric oid = OIDGeneric.getInstance(data);
                    extras.put(oid.getOid(), oid);
                } else if (tipo.equals(ONE)) {
                    email = (String) e2;
                }
            }
        } catch (CertificateParsingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the certificate is an "ICP-BRASIL Pessoa Física"
     *
     * @return True if you are an "ICP-BRASIL Pessoa Física". False otherwise.
     */
    public boolean isCertificatePF() {
    	// oid for dados-pf(1)
        return extras.get("2.16.76.1.3.1") != null;
    }

    /**
     * Checks if the certificate is an "ICP-BRASIL Pessoa Jurídica"
     *
     * @return True if you are an "ICP-BRASIL Pessoa Jurídica ". False otherwise.
     */
    public boolean isCertificatePJ() {
    	// oid for (cei-pj)
        return extras.get("2.16.76.1.3.7") != null;
    }

    /**
     * Checks if the certificate is an "ICP-BRASIL Equipment"
     *
     * @return True, True if you are an "ICP-BRASIL Equipment". False otherwise.
     */
    public boolean isCertificateEquipment() {
    	// OID nome-cnpj(8) only for Equipament
        return extras.get("2.16.76.1.3.8") != null;
    }

    /**
     * Class OID 2.16.76.1.3.1 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Fisica" attributes<br>
     *  </ b> Date of birth of the holder on "DDMMAAAA" format<br>
     * <B> * </ b> The Brazilian IRS Individuals Registry called CPF <br>
     * <B> * </ b> Brazilian Social Identity Number - NIS (PIS, PASEP or CI) <br>
     * <B> * </ b> the Brazilian ID number called RG <br>
     * <B> * </ b> the initials of the issuing agency of the ID (RG) <br>
     * <B> * </ b> UF (Initials for a Brasilian state) of the issuing agency of the RG <br>
     * @return OID_2_16_76_1_3_1
     */
    public OID_2_16_76_1_3_1 getOID_2_16_76_1_3_1() {  	
    	return (OID_2_16_76_1_3_1) extras.get("2.16.76.1.3.1");
    }

    /**
     * Class OID 2.16.76.1.3.5 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Fisica" attributes<br>
     * <b>*</b> Number of Electoral document (Titulo de Eleitor) <br>
     * <b>*</b> Zone of Electoral document <br>
     * <b>*</b> Section of  Electoral document <br>
     * <b>*</b> City of  Electoral document <br>
     * <b>*</b> UF (Initials for a Brasilian state) of Electoral document<br>
     *
     * @return OID_2_16_76_1_3_5
     */
    public OID_2_16_76_1_3_5 getOID_2_16_76_1_3_5() {
        return (OID_2_16_76_1_3_5) extras.get("2.16.76.1.3.5");
    }

    /**
     * Class OID 2.16.76.1.3.6 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Fisica" attributes<br>
     * <b>*</b> Brazilian Social Identification Number (INSS-CEI) of the holder of certificate<br>
     *
     * @return OID_2_16_76_1_3_6
     */
    public OID_2_16_76_1_3_6 getOID_2_16_76_1_3_6() {
        return (OID_2_16_76_1_3_6) extras.get("2.16.76.1.3.6");
    }

    /**
     * Class OID 2.16.76.1.3.2 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Juridica and Equipment" attributes<br>
     * <b>*</b> Name of the person responsible for the certificate <br>
     *
     * @return OID_2_16_76_1_3_2
     */
    public OID_2_16_76_1_3_2 getOID_2_16_76_1_3_2() {
        return (OID_2_16_76_1_3_2) extras.get("2.16.76.1.3.2");
    }

    /**
     * Class OID 2.16.76.1.3.3 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Juridica and Equipment" attributes<br>
     * <b>*</b> the Brazilian IRS's Bussiness Company Registry Number called CNPJ (Cadastro Nacional de Pessoa Juridica) <br>
     *
     * @return OID_2_16_76_1_3_3
     */
    public OID_2_16_76_1_3_3 getOID_2_16_76_1_3_3() {
        return (OID_2_16_76_1_3_3) extras.get("2.16.76.1.3.3");
    }

    /**
     * Class OID 2.16.76.1.3.4 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Juridica and Equipment" attributes<br>
     * <b>*</b> Date of birth of the person responsible for the certificate, in ddMMyyyy format <br>
     * <b>*</b> the Brazilian IRS Individuals Registry number called CPF (Cadastro de Pessoa Fisica) of the
     * Responsible <br>
     * <b>*</b>Brazilian Social Identification number - initials are: NIS (PIS, PASEP or CI) of the responsible <br>
     * <b>*</b> the Brazilian ID number (called RG) of the responsible for the certificate <br>
     * <b>*</b> the initials of the issuing agency of the ID (RG) <br>
     * <b>*</b> Initials for a Brasilian state(UF) of the issuing agency of the ID (RG) <br>
     *
     * @return OID_2_16_76_1_3_4
     */
    public OID_2_16_76_1_3_4 getOID_2_16_76_1_3_4() {
        return (OID_2_16_76_1_3_4) extras.get("2.16.76.1.3.4");
    }

    /**
     * Class OID 2.16.76.1.3.7 <br>
     * <br>
     * Has some "ICP-BRASIL Pessoa Juridica" attributes<br>
     * <b>*</b> number of Specific Registry (called CEI), on 
	 * Brazilian National Institute of Social Security,  of the bussines company holding the certificate<br>
     *
     * @return OID_2_16_76_1_3_7
     */
    public OID_2_16_76_1_3_7 getOID_2_16_76_1_3_7() {
        return (OID_2_16_76_1_3_7) extras.get("2.16.76.1.3.7");
    }

    /**
     * Class OID 2.16.76.1.3.8 <br>
     * <br>
     * Has some "ICP-BRASIL Equipment" attributes<br>
     *  Corporate name in the the Brazilian IRS's Bussiness Company Registry Number called CNPJ without abbreviations, 
     * 
     *
     * @return OID_2_16_76_1_3_8
     */
    public OID_2_16_76_1_3_8 getOID_2_16_76_1_3_8() {
        return (OID_2_16_76_1_3_8) extras.get("2.16.76.1.3.8");
    }

    /**
     *
     * @return the e-mail for certificate.
     */
    public String getEmail() {
        return email;
    }

}
