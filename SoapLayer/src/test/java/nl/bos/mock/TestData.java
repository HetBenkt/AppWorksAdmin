package nl.bos.mock;

public enum TestData {
    INSTANCE;

    final String soapMessage = """
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:wsse="urn:api.ecm.opentext.com">
            	<SOAP-ENV:Header xmlns:wsse="urn:api.ecm.opentext.com" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            		<header xmlns:wsse="urn:api.ecm.opentext.com" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://schemas.cordys.com/General/1.0/">
            			<msg-id>080027c2-550f-a1ed-8700-cc06a8f8261d</msg-id>
            			<messageoptions noreply="true"/>
            		</header>
            	</SOAP-ENV:Header>
            	<SOAP-ENV:Body>
            		<samlp:Response xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" IssueInstant="2022-08-14T18:59:13.590Z" MajorVersion="1" MinorVersion="1" ResponseID="A080027c2-550f-a1ed-8700-cc06be6a261d">
            			<Signature xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns="http://www.w3.org/2000/09/xmldsig#">
            				<SignedInfo>
            					<CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            					<SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
            					<Reference URI="#A080027c2-550f-a1ed-8700-cc06c236a61d">
            						<Transforms>
            							<Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
            							<Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            						</Transforms>
            						<DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
            						<DigestValue>JvZcy2WwXwmWUZG6ug5XwAGBhTaZsppEOI88ngiOMmk=</DigestValue>
            					</Reference>
            				</SignedInfo>
            				<SignatureValue>XdJ+Op8BId3DyVgnyQqEZuiotqv+AJH3lLQuJNnt8C7B2Hp70j04JqZpi06i9yBJlABORWFiF5mz
            					/MW0ebx7lQlqr81O0NzyqhH92HHU1kkKUVQY92DQhtd5Y5nHQkM8OkCIo+qfEMH42H90bIZku+FZ
            					N69XFI+oAUAsYSY1iL6Rp4bdaE08/eY+ZEFLeFox/i41i08c6Y4Ia9QHYuLBIr1ANI/4Ii68/FZu
            					fbXtGds5GeqDVhnRu4XpPw5zTl7ENlSnygeaboUnsOcSWTb/XaknWKxuOkosbuVRqmDtQ9/a3rcn
            					EKqm89pD9gITF4CvGXo2w/gghE92D0WH7aZjEQ==</SignatureValue>
            			</Signature>
            			<samlp:Status xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol">
            				<samlp:StatusCode Value="samlp:Success"/>
            			</samlp:Status>
            			<saml:Assertion xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" AssertionID="A080027c2-550f-a1ed-8700-cc06c236a61d" IssueInstant="2022-08-14T18:59:13.591Z" Issuer="https://www.cordys.com/SSO" MajorVersion="1" MinorVersion="1">
            				<saml:Conditions NotBefore="2022-08-14T18:54:13.590Z" NotOnOrAfter="2022-08-15T02:59:13.590Z"/>
            				<saml:AuthenticationStatement AuthenticationInstant="2022-08-14T18:59:13.590Z" AuthenticationMethod="urn:oasis:names:tc:SAML:1.0:am:password">
            					<saml:Subject>
            						<saml:NameIdentifier>anonymous</saml:NameIdentifier>
            					</saml:Subject>
            				</saml:AuthenticationStatement>
            			</saml:Assertion>
            			<samlp:AssertionArtifact xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol">e0pBVkEtQUVTL0dDTS9Ob1BhZGRpbmd9WWGZ9li84Pl0ZLM4LqD57o8Q1lUNUlfFxq8t+1kg7pbbtnEe4zaDioNaAELZioW3Flz7G0e4hHeR/Mv2/ibul6rCzX5FoCs0BciHSbOc5M7dfcY2fC/Np8dRefcVWQZKW58u</samlp:AssertionArtifact>
            		</samlp:Response>
            	</SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            """;

    final String jsonMessage = """
            {
               "token":"6F7464735F73657373696F6E5F6B6579",
               "userId":"awdev@awp",
               "ticket":"*VER2*ABRhBKQxWCK_ETWlxqgGal7AiACg6wAQU0oTRMPfhFOm3iEFGne0qwKQ0nVQ-U08c7Tld42YQ6lpWcAxgvkqetKbiPmWoXupu_QUMh88AVfOFsaaOzpqsn2sQx9N6b9IZ68oNKXOQDVmI1ZnrdzuH-K6C1A_oEHq89kN9TBJ4EZK3uFK2zlVOpV3HfLAIqN5Te_entdIPdU4Mh5pSLsg4Bi3ioTXBWNbQDdCcJ-DWOSTddShgrJNTUrgxJggHeQKEaQIIRji3Wx8lZEWM4s7te7rhJrgdTm8UDjgoyFg4nwijlvZfpoyC33542Ti7ejwnkt6Ij5f8Qzy_f2DoliJSZoMl63ewZwd362Lka0--qfa8YsHXMvShU6NoRHtnS7U56OXZDHAdU5Y3dnxij891lOzJBuSaVyHihIqXCo2Ri-LTwcbJNJJ_c6OkO7rs29tD51iP0kDu50C8REedS3vOpylj_9atf7LsfDYDa6IsovAGgFYZmbkYoEY_Hml_Lh1Jxw4lK3mPbb1Avmc99Pls3yk-5veNVzPhWRifESLYZueeF2KLZyOymJeNEAgr8FabCQKISi7Z-g53WLV3atxQ7x0b6chIZPWymEL_gAkQ4H_7Mne8vx37gdaIIvBqQ4Az1l6lwreUtkLiX2mwPr4AEfyYMh_2bwnbFYelKTaA04yHEOxgz9e57_PaFnTz3ph1Q7vUmBTw2KiR9HKfRvFuavB1m3D7CPrf8070oIPhUhcFnhkDOTAKpgQ7gdnKYOvLCzYi6-E2v6q_ivk3HZx_-RMldxLtGchQCHSy2T6gfo8DjLrJ5kSRyhJqjZre9zDFMIyV2aeLfJ81eELzRQqFqZsyQSkA-Xpgl0-phOHq_Rcsv0um22kRLjJ4IfatjKi3X9axmEAM56257qOfps0fLJQ-L9-4DJBNpY*",
               "resourceID":"7a3eb4ce-b1ec-4acb-bb5c-45e79df2830e",
               "failureReason":null,
               "passwordExpirationTime":0,
               "continuation":false,
               "continuationContext":null,
               "continuationData":null
            }
            """;

    //TODO make a valid message from the platform
    final String jsonHealthMessage = "{\"name\":\"John\", \"age\":30, \"car\":null}";
}
