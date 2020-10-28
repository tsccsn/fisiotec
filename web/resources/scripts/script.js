        var j0 = false;
        var j1 = false;
        var j2 = false;
        var j3 = false;
        var j4 = false;
        var j5 = false;
        var j6 = false;
        var alinhamentoBurro = false;
        var tempoDafault = 300;
        var local = "http://localhost:8084/fisiotec/";
        function apenasNumeros(componente) {
            var codTecla = event.keyCode;
            var textoAtual = document.getElementById(componente).value;
            if(codTecla < 48){
                if (codTecla == 46) {
                    if (textoAtual.indexOf('.') == -1 ){
                        return true;
                    }else  {
                        return false;
                    }
                } else if (codTecla == 8 || codTecla == 9 || codTecla == 37 || codTecla == 39 ) {
                    return true
                }else{
                    return false;
                }	
            } else if(codTecla > 57)  {
                return false;
            }
        }
        function apenasNumerosInteiros(componente) {
            var codTecla = event.keyCode;
            if(codTecla < 48){
                if (codTecla == 8 || codTecla == 9 || codTecla == 37 || codTecla == 39 ) {
                    return true
                }else{
                    return false;
                }	
            } else if(codTecla > 57)  {
                return false;
            }
        }

        function stopaAlinhamentoBurro(){
            alinhamentoBurro = false;
        }

        function alinhaDeFormaBurra(){
            atualiza();
            if(!j0 && !j1 && !j2 && !j3 && !j4 && !j5 && !j6){
                alinhamentoBurro = false;
            }
            if(alinhamentoBurro){
                setTimeout('alinhaDeFormaBurra()', 10);   
            }
        }
        function iniciaAlinhamentoBurro(){
            alinhamentoBurro = true;
            alinhaDeFormaBurra();
        }

        function terminaAlinhamentoBurro(){
            alinhamentoBurro = false;
        }

        // # ALINHAMENTO
        function atualiza(){
            if(j0){
                pocisiona(janela0);
            }
            if(j1){
                pocisiona(janela1);
            }
            if(j2){
                pocisiona(janela2);
            }
            if(j3){
                pocisiona(janela3);
            }
            if(j4){
                pocisiona(janela4);
            }
            if(j5){
                pocisiona(janela5);
            }
            if(j6){
                pocisiona(janela6);
            }
        }
        function pocisiona(deQuem){
            var largura = window.innerWidth;
            var altura = window.innerHeight;
            var larguraJanela = deQuem.offsetWidth;
            var alturaJanela = deQuem.offsetHeight;
            var top = ((altura - alturaJanela) / 2);
            var left = ((largura - larguraJanela)  / 2) ;
            deQuem.style.left = left + "px";
            if(top < 0){
                deQuem.style.top = 0 + "px";
            }else{
                deQuem.style.top = top + "px";
            }
        }
        // # FIM ALINHAMENTO        

        // # CLREAMENTO
        function mudaLuz(opacidadeAtual, escureceAte, emTantoTempo, dequem) {
            for (i = 0; i < emTantoTempo; i++) {
                valor = opacidadeAtual
                + (((escureceAte - opacidadeAtual) / emTantoTempo) * i);
                setTimeout('mudaOpacidadeFundo(' + valor + ',' + dequem + ')', i);
            }
        }
        function mudaOpacidadeFundo(valor, deQuem) {
            deQuem.style.opacity = valor;
        }
        // # FIM CLREAMENTO

        // # BREU 
        function breuVisivel() {
            breu.style.visibility = 'visible';
        }
        function breuInisivel() {
            breu.style.visibility = 'hidden';
        }
        function breuOff(){
            mudaLuz(0.5,0,tempoDafault,'breu');
            setTimeout('breuInisivel()', tempoDafault+1);
        }
        function breuOn(){
            breuVisivel();
            mudaLuz(0,0.5,tempoDafault,'breu');
        }
        function maisBreu(){
            mudaLuz(0.5,0.8,tempoDafault,'breu');
        }
        function menosBreu(){
            mudaLuz(0.8,0.5,tempoDafault,'breu');
        }
        // # FIM BREU


        // # BREU NA JANELA 
        function breuNaJanelaOff(janela){
            mudaLuz(0.3,0,tempoDafault,janela);
            setTimeout(janela+".style.visibility = 'hidden'", tempoDafault+1);
        }
        function breuOnNaJanela(janela){
            setTimeout(janela+".style.visibility = 'visible'", 0);
            mudaLuz(0,0.3,tempoDafault,janela);
        }
        // # FIM BREU NA JANELA 


        function abreJanela0(){ 
            janelaRolavel0.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela0');
            j0 = true;
            atualiza();

        }
        function fechaJanela0(){
            mudaLuz(1, 0, tempoDafault, 'janela0');
            j0 = false;
            setTimeout("janelaRolavel0.style.visibility = 'hidden'", tempoDafault+1);
        }

        function abreJanela1(){ 
            janelaRolavel1.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela1');
            j1 = true;
            atualiza();
        }
        function fechaJanela1(){
            mudaLuz(1, 0, tempoDafault, 'janela1');
            j1 = false;
            setTimeout("janelaRolavel1.style.visibility = 'hidden'", tempoDafault+1);
        }

        function abreJanela2(){ 
            janelaRolavel2.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela2');
            j2 = true;
            atualiza();
        }
        function fechaJanela2(){
            mudaLuz(1, 0, tempoDafault, 'janela2');
            j2 = false;
            setTimeout("janelaRolavel2.style.visibility = 'hidden'", tempoDafault+1);
        }

        function abreJanela3(){ 
            janelaRolavel3.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela3');
            j3 = true;
            atualiza();
        }
        function fechaJanela3(){
            mudaLuz(1, 0, tempoDafault, 'janela3');
            j3 = false;
            setTimeout("janelaRolavel3.style.visibility = 'hidden'", tempoDafault+1);
        }

        function abreJanela4(){ 
            janelaRolavel4.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela4');
            j4 = true;
            atualiza();
        }
        function fechaJanela4(){
            mudaLuz(1, 0, tempoDafault, 'janela4');
            j4 = false;
            setTimeout("janelaRolavel4.style.visibility = 'hidden'", tempoDafault+1);
        }

        function abreJanela5(){ 
            janelaRolavel5.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela5');
            j5 = true;
            atualiza();
        }
        function fechaJanela5(){
            mudaLuz(1, 0, tempoDafault, 'janela5');
            j5 = false;
            setTimeout("janelaRolavel5.style.visibility = 'hidden'", tempoDafault+1);
        }
        function abreJanela6(){ 
            janelaRolavel6.style.visibility = 'visible';
            mudaLuz(0, 1, tempoDafault, 'janela6');
            j6 = true;
            atualiza();
        }
        function fechaJanela6(){
            mudaLuz(1, 0, tempoDafault, 'janela6');
            j6 = false;
            setTimeout("janelaRolavel6.style.visibility = 'hidden'", tempoDafault+1);
        }

        if(window.attachEvent){
            window.attachEvent("onresize",function() {
                atualiza();
            });
        }else{
            window.addEventListener("resize",function() {
                atualiza();
            });
        }
        function redericiona(prEndereco){
		location.href=prEndereco;
	}
        function treme(prComponente){
            var i = 0;
            while(i < 300){
                setTimeout(" alteraMargin("+prComponente+",'20px')  ; ",i*1.1);
                setTimeout(" alteraMargin("+prComponente+",'-20px')  ; ",i*1.2);
                i++;
            }
            setTimeout(" alteraMargin("+prComponente+",'0px')  ; ",300*1.2+1);
        }
        function alteraMargin(prComponente, prValor){
            prComponente.style.marginLeft = prValor;
        }
        
        
        var objetos = new Array();
        var tremendo = new Array();
        var orientacao = new Array();

        function zeraObjetos(){
            for (i = 0; i < objetos.length; i++) {
                document.getElementById(objetos[i]).style.margin = '0px';
            }
            objetos = new Array();
            tremendo = new Array();
            orientacao = new Array();

        }

        function addComponenteNaLista(prComp){
            objetos[objetos.length] = prComp;
            tremendo[tremendo.length] = false;
            orientacao[orientacao.length] = false;
        }
        
        function confereComponentes(){
            for (i = 0; i < objetos.length; i++) {
                if(document.getElementById(objetos[i]).value == "" ) {
                    tremendo[i] = true;
                    treme(i);
                }
            }
        }

        function treme(pos){
            if(tremendo[pos]){
                if(orientacao[pos]){
                    orientacao[pos] = false;
                    setTimeout("document.getElementById(objetos["+pos+"]).style.marginLeft = '20px';",20);
                }else{
                    setTimeout("document.getElementById(objetos["+pos+"]).style.marginLeft = '0px';",20);
                    orientacao[pos] = true;	
                }
                setTimeout("treme("+pos+");",40);
            }else{
                setTimeout("document.getElementById(objetos["+pos+"]).style.margin = '0px';",25);
            }
        }
        function para(pos){
            tremendo[pos] = false;
        }
                // # !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                // # FUNÇÕES DE RETORNO DE MÉTODOS DOS BENAS    
                // # BREU NA JANELA 
                function resultadoTentativaLoginProfessor(args){
                    if(args.treme){
                        var i = 0;
                        while(i < 300){
                            setTimeout("document.getElementById('formularioLoginProfessor').style.paddingRight = '20px'",i*1.1);
                            setTimeout("document.getElementById('formularioLoginProfessor').style.paddingRight = '0px'",i*1.2);
                            setTimeout("document.getElementById('formularioLoginProfessor').style.paddingLeft = '20px'",i*1.3);
                            setTimeout("document.getElementById('formularioLoginProfessor').style.paddingLeft = '0px'",i*1.4);
                            i++;
                        }
                        setTimeout("document.getElementById('formularioLoginProfessor').style.padding = '0px'",300*1.2);
                    }
                    if(args.logou){
                        redericiona(local+"portal/professor/inicio.xhtml");
                    }
                }   
                // # FIM !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                // # FIM FUNÇÕES DE RETORNO DE MÉTODOS DOS BENAS    
              