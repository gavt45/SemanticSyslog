<?xml version="1.0" encoding="UTF-8"?>
<model ref="r:aadca3f3-b23f-458a-9e60-a974fab8ed8a(SemanticSyslogSolution.SemanticSyslog)">
  <persistence version="9" />
  <languages>
    <use id="33179490-6db2-4c80-bd28-66b0152ec77c" name="SemanticLanguage" version="0" />
  </languages>
  <imports />
  <registry>
    <language id="ceab5195-25ea-4f22-9b92-103b95ca8c0c" name="jetbrains.mps.lang.core">
      <concept id="1169194658468" name="jetbrains.mps.lang.core.structure.INamedConcept" flags="ng" index="TrEIO">
        <property id="1169194664001" name="name" index="TrG5h" />
      </concept>
    </language>
    <language id="33179490-6db2-4c80-bd28-66b0152ec77c" name="SemanticLanguage">
      <concept id="5125628472001484671" name="SemanticLanguage.structure.NumericConstant" flags="ng" index="3pNif">
        <property id="5125628472001484676" name="value" index="3pNhO" />
      </concept>
      <concept id="5125628472007286910" name="SemanticLanguage.structure.NumericType" flags="ng" index="3JCee" />
      <concept id="3736865676534609094" name="SemanticLanguage.structure.DomainEmptyLine" flags="ng" index="24Vvte" />
      <concept id="9101935343136992136" name="SemanticLanguage.structure.FunctionCall" flags="ng" index="e47DK">
        <reference id="9101935343136992137" name="definition" index="e47DL" />
        <child id="9101935343136992138" name="args" index="e47DM" />
      </concept>
      <concept id="9101935343136523816" name="SemanticLanguage.structure.FunctionDef" flags="ng" index="eqlvg">
        <child id="9101935343136523817" name="args" index="eqlvh" />
        <child id="9101935343136523818" name="result" index="eqlvi" />
      </concept>
      <concept id="8918277825771451230" name="SemanticLanguage.structure.ArgumentReference" flags="ng" index="eBqkK">
        <reference id="8918277825771451231" name="declaration" index="eBqkL" />
      </concept>
      <concept id="5180555686068913243" name="SemanticLanguage.structure.DomainSpecificModel" flags="ng" index="2oCoeD">
        <child id="5180555686068913699" name="elements" index="2oCo7h" />
      </concept>
      <concept id="2073504467208085352" name="SemanticLanguage.structure.LogicalType" flags="nn" index="2$QgSV" />
      <concept id="2073504467209504078" name="SemanticLanguage.structure.StringType" flags="ng" index="2$WXgt" />
      <concept id="2073504467209342143" name="SemanticLanguage.structure.VarDeclaration" flags="ng" index="2$X5RG">
        <child id="2073504467209342228" name="initializer" index="2$X5L7" />
      </concept>
      <concept id="2073504467209348321" name="SemanticLanguage.structure.VarReference" flags="ng" index="2$X7mM">
        <reference id="2073504467209348322" name="declaration" index="2$X7mL" />
      </concept>
      <concept id="3308300503039647678" name="SemanticLanguage.structure.IfStatement" flags="ng" index="2C1uTT">
        <child id="3308300503039647684" name="trueBranch" index="2C1uS3" />
        <child id="3308300503039647680" name="condition" index="2C1uS7" />
      </concept>
      <concept id="2537342212761094603" name="SemanticLanguage.structure.Usage" flags="ng" index="2M3fE7">
        <property id="2826170137865511778" name="classname" index="1xsJ6A" />
        <reference id="2537342212761094604" name="contract" index="2M3fE0" />
      </concept>
      <concept id="2537342212761018602" name="SemanticLanguage.structure.SemanticModel" flags="ng" index="2M3LeA">
        <child id="3821515829481183763" name="body" index="1UMHDP" />
      </concept>
      <concept id="7710564681170176918" name="SemanticLanguage.structure.CheckAll" flags="ng" index="3dOMoJ">
        <child id="7710564681170176919" name="commands" index="3dOMoI" />
      </concept>
      <concept id="2865360063749950725" name="SemanticLanguage.structure.ListType" flags="ng" index="3mEW3e">
        <child id="2865360063750225683" name="elementsType" index="3mFZbo" />
      </concept>
      <concept id="5069851822860934581" name="SemanticLanguage.structure.StringConstant" flags="ng" index="1z9qrE">
        <property id="5069851822860934587" name="value" index="1z9qr$" />
        <child id="9186560810175004694" name="concats" index="pvbxO" />
      </concept>
      <concept id="5069851822853563574" name="SemanticLanguage.structure.ArgumentDeclaration" flags="ng" index="1zlxZD">
        <child id="5069851822853564046" name="type" index="1zlxRh" />
      </concept>
      <concept id="2074653526560201063" name="SemanticLanguage.structure.CommandList" flags="ng" index="3IqRW4">
        <child id="2074653526560201351" name="commands" index="3IqRN$" />
      </concept>
      <concept id="2074653526560551999" name="SemanticLanguage.structure.PredicateDef" flags="ng" index="3Irp9s">
        <child id="5069851822853640179" name="args" index="1zlniG" />
        <child id="2074653526560552030" name="result" index="3Irp8X" />
      </concept>
      <concept id="3821515829481180482" name="SemanticLanguage.structure.EmptyLine" flags="ng" index="1UMGO$" />
    </language>
  </registry>
  <node concept="2oCoeD" id="22ZHBX2i9nv">
    <property role="TrG5h" value="SyslogDSL" />
    <node concept="eqlvg" id="22ZHBX2i9nz" role="2oCo7h">
      <property role="TrG5h" value="alert tg" />
      <node concept="2$QgSV" id="22ZHBX2i9o_" role="eqlvi" />
      <node concept="1zlxZD" id="22ZHBX2i9nR" role="eqlvh">
        <property role="TrG5h" value="adminId" />
        <node concept="2$WXgt" id="22ZHBX2i9o7" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2i9od" role="eqlvh">
        <property role="TrG5h" value="msg" />
        <node concept="2$WXgt" id="22ZHBX2i9ov" role="1zlxRh" />
      </node>
    </node>
    <node concept="eqlvg" id="22ZHBX2igpQ" role="2oCo7h">
      <property role="TrG5h" value="contains" />
      <node concept="1zlxZD" id="22ZHBX2igqI" role="eqlvh">
        <property role="TrG5h" value="str" />
        <node concept="2$WXgt" id="22ZHBX2igr0" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2igr6" role="eqlvh">
        <property role="TrG5h" value="substr" />
        <node concept="2$WXgt" id="22ZHBX2igrs" role="1zlxRh" />
      </node>
      <node concept="2$QgSV" id="22ZHBX2igrE" role="eqlvi" />
    </node>
    <node concept="eqlvg" id="22ZHBX2ihp0" role="2oCo7h">
      <property role="TrG5h" value="get str from list" />
      <node concept="1zlxZD" id="22ZHBX2ihqC" role="eqlvh">
        <property role="TrG5h" value="list" />
        <node concept="3mEW3e" id="22ZHBX2ihqS" role="1zlxRh">
          <node concept="2$WXgt" id="22ZHBX2ihr5" role="3mFZbo" />
        </node>
      </node>
      <node concept="1zlxZD" id="22ZHBX2ihre" role="eqlvh">
        <property role="TrG5h" value="index" />
        <node concept="3JCee" id="22ZHBX2ihrA" role="1zlxRh" />
      </node>
      <node concept="2$WXgt" id="22ZHBX2ihrG" role="eqlvi" />
    </node>
    <node concept="eqlvg" id="22ZHBX2igFL" role="2oCo7h">
      <property role="TrG5h" value="split get" />
      <node concept="1zlxZD" id="22ZHBX2igH9" role="eqlvh">
        <property role="TrG5h" value="str" />
        <node concept="2$WXgt" id="22ZHBX2igHr" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2igHx" role="eqlvh">
        <property role="TrG5h" value="sep" />
        <node concept="2$WXgt" id="22ZHBX2igHN" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2igHT" role="eqlvh">
        <property role="TrG5h" value="index" />
        <node concept="3JCee" id="22ZHBX2igIh" role="1zlxRh" />
      </node>
      <node concept="2$WXgt" id="22ZHBX2igIn" role="eqlvi" />
    </node>
    <node concept="eqlvg" id="22ZHBX2iguo" role="2oCo7h">
      <property role="TrG5h" value="match regex" />
      <node concept="1zlxZD" id="22ZHBX2igvw" role="eqlvh">
        <property role="TrG5h" value="regex" />
        <node concept="2$WXgt" id="22ZHBX2igvI" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2igvO" role="eqlvh">
        <property role="TrG5h" value="str" />
        <node concept="2$WXgt" id="22ZHBX2igw6" role="1zlxRh" />
      </node>
      <node concept="2$QgSV" id="22ZHBX2igwc" role="eqlvi" />
    </node>
    <node concept="eqlvg" id="22ZHBX2i9oW" role="2oCo7h">
      <property role="TrG5h" value="find regex" />
      <node concept="3mEW3e" id="22ZHBX2i9qM" role="eqlvi">
        <node concept="2$WXgt" id="22ZHBX2i9qY" role="3mFZbo" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2i9p$" role="eqlvh">
        <property role="TrG5h" value="regex" />
        <node concept="2$WXgt" id="22ZHBX2i9pO" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2i9pU" role="eqlvh">
        <property role="TrG5h" value="str" />
        <node concept="2$WXgt" id="22ZHBX2i9qe" role="1zlxRh" />
      </node>
      <node concept="1zlxZD" id="22ZHBX2i9qk" role="eqlvh">
        <property role="TrG5h" value="group" />
        <node concept="3JCee" id="22ZHBX2i9qG" role="1zlxRh" />
      </node>
    </node>
    <node concept="24Vvte" id="22ZHBX2i9r6" role="2oCo7h" />
    <node concept="eqlvg" id="22ZHBX2i9sa" role="2oCo7h">
      <property role="TrG5h" value="start" />
      <node concept="2$QgSV" id="22ZHBX2i9sU" role="eqlvi" />
      <node concept="1zlxZD" id="22ZHBX2i9u_" role="eqlvh">
        <property role="TrG5h" value="callback predicate name" />
        <node concept="2$WXgt" id="22ZHBX2i9uV" role="1zlxRh" />
      </node>
    </node>
  </node>
  <node concept="2M3LeA" id="22ZHBX2i9t2">
    <property role="TrG5h" value="SemanticSyslog" />
    <node concept="3IqRW4" id="22ZHBX2i9t3" role="1UMHDP">
      <node concept="2M3fE7" id="22ZHBX2i9tJ" role="3IqRN$">
        <property role="1xsJ6A" value="com.gumirov.semanticsyslog.SyslogDSL" />
        <ref role="2M3fE0" node="22ZHBX2i9nv" resolve="SyslogDSL" />
      </node>
      <node concept="1UMGO$" id="22ZHBX2i9t$" role="3IqRN$" />
      <node concept="3Irp9s" id="22ZHBX2i9wW" role="3IqRN$">
        <property role="TrG5h" value="parse line" />
        <node concept="3dOMoJ" id="22ZHBX2i9xh" role="3Irp8X">
          <node concept="2$X5RG" id="22ZHBX2igQ7" role="3dOMoI">
            <property role="TrG5h" value="message" />
            <node concept="e47DK" id="22ZHBX2igU5" role="2$X5L7">
              <ref role="e47DL" node="22ZHBX2igFL" resolve="split get" />
              <node concept="eBqkK" id="22ZHBX2igV1" role="e47DM">
                <ref role="eBqkL" node="22ZHBX2i9xu" resolve="line" />
              </node>
              <node concept="1z9qrE" id="22ZHBX2igWL" role="e47DM">
                <property role="1z9qr$" value="\\|" />
              </node>
              <node concept="3pNif" id="22ZHBX2igYJ" role="e47DM">
                <property role="3pNhO" value="3" />
              </node>
            </node>
          </node>
          <node concept="2$X5RG" id="22ZHBX2ihxg" role="3dOMoI">
            <property role="TrG5h" value="sourceMmatches" />
            <node concept="e47DK" id="22ZHBX2ihC7" role="2$X5L7">
              <ref role="e47DL" node="22ZHBX2i9oW" resolve="find regex" />
              <node concept="1z9qrE" id="22ZHBX2ihEA" role="e47DM">
                <property role="1z9qr$" value="src=&quot;(.{7,15}):.*&quot;" />
              </node>
              <node concept="2$X7mM" id="22ZHBX2ihH3" role="e47DM">
                <ref role="2$X7mL" node="22ZHBX2igQ7" resolve="message" />
              </node>
              <node concept="3pNif" id="22ZHBX2ihJA" role="e47DM">
                <property role="3pNhO" value="1" />
              </node>
            </node>
          </node>
          <node concept="2$X5RG" id="22ZHBX2ij1J" role="3dOMoI">
            <property role="TrG5h" value="dstPortMatches" />
            <node concept="e47DK" id="22ZHBX2ija7" role="2$X5L7">
              <ref role="e47DL" node="22ZHBX2i9oW" resolve="find regex" />
              <node concept="1z9qrE" id="22ZHBX2ijem" role="e47DM">
                <property role="1z9qr$" value="dst=&quot;.{7,15}:(.{2,5})&quot;" />
              </node>
              <node concept="2$X7mM" id="22ZHBX2ijG9" role="e47DM">
                <ref role="2$X7mL" node="22ZHBX2igQ7" resolve="message" />
              </node>
              <node concept="3pNif" id="22ZHBX2ijKy" role="e47DM">
                <property role="3pNhO" value="1" />
              </node>
            </node>
          </node>
          <node concept="2$X5RG" id="22ZHBX2ihV1" role="3dOMoI">
            <property role="TrG5h" value="ip" />
            <node concept="e47DK" id="22ZHBX2ihYh" role="2$X5L7">
              <ref role="e47DL" node="22ZHBX2ihp0" resolve="get str from list" />
              <node concept="2$X7mM" id="22ZHBX2ii1t" role="e47DM">
                <ref role="2$X7mL" node="22ZHBX2ihxg" resolve="sourceMmatches" />
              </node>
              <node concept="3pNif" id="22ZHBX2ii4T" role="e47DM">
                <property role="3pNhO" value="0" />
              </node>
            </node>
          </node>
          <node concept="2$X5RG" id="22ZHBX2ijPw" role="3dOMoI">
            <property role="TrG5h" value="targetPort" />
            <node concept="e47DK" id="22ZHBX2ik1i" role="2$X5L7">
              <ref role="e47DL" node="22ZHBX2ihp0" resolve="get str from list" />
              <node concept="2$X7mM" id="22ZHBX2ikda" role="e47DM">
                <ref role="2$X7mL" node="22ZHBX2ij1J" resolve="dstPortMatches" />
              </node>
              <node concept="3pNif" id="22ZHBX2ikaI" role="e47DM">
                <property role="3pNhO" value="0" />
              </node>
            </node>
          </node>
          <node concept="2C1uTT" id="22ZHBX2igwp" role="3dOMoI">
            <node concept="e47DK" id="22ZHBX2igwJ" role="2C1uS7">
              <ref role="e47DL" node="22ZHBX2iguo" resolve="match regex" />
              <node concept="1z9qrE" id="22ZHBX2igx8" role="e47DM">
                <property role="1z9qr$" value=".*dst=.{7,15}:(1234|22|443).*DROPPED.*" />
              </node>
              <node concept="eBqkK" id="22ZHBX2igxR" role="e47DM">
                <ref role="eBqkL" node="22ZHBX2i9xu" resolve="line" />
              </node>
            </node>
            <node concept="e47DK" id="22ZHBX2igyL" role="2C1uS3">
              <ref role="e47DL" node="22ZHBX2i9nz" resolve="alert tg" />
              <node concept="1z9qrE" id="22ZHBX2igzp" role="e47DM" />
              <node concept="1z9qrE" id="22ZHBX2ig$I" role="e47DM">
                <property role="1z9qr$" value="Ломятся на порт с ip: " />
                <node concept="2$X7mM" id="22ZHBX2ii6R" role="pvbxO">
                  <ref role="2$X7mL" node="22ZHBX2ihV1" resolve="ip" />
                </node>
                <node concept="1z9qrE" id="22ZHBX2iki3" role="pvbxO">
                  <property role="1z9qr$" value=" на порт: " />
                </node>
                <node concept="2$X7mM" id="22ZHBX2ikni" role="pvbxO">
                  <ref role="2$X7mL" node="22ZHBX2ijPw" resolve="targetPort" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="1zlxZD" id="22ZHBX2i9xu" role="1zlniG">
          <property role="TrG5h" value="line" />
          <node concept="2$WXgt" id="22ZHBX2i9xC" role="1zlxRh" />
        </node>
      </node>
      <node concept="1UMGO$" id="22ZHBX2i9u9" role="3IqRN$" />
      <node concept="3Irp9s" id="22ZHBX2i9t7" role="3IqRN$">
        <property role="TrG5h" value="start" />
        <node concept="3dOMoJ" id="22ZHBX2i9te" role="3Irp8X">
          <node concept="e47DK" id="22ZHBX2i9tV" role="3dOMoI">
            <ref role="e47DL" node="22ZHBX2i9sa" resolve="start" />
            <node concept="1z9qrE" id="22ZHBX2i9vd" role="e47DM">
              <property role="1z9qr$" value="parse line" />
            </node>
          </node>
        </node>
      </node>
    </node>
  </node>
</model>

