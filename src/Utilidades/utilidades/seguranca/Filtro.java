/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.seguranca;

import beans.sessoes.BeanEstoque;
import beans.sessoes.BeanPortal;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thiago
 */
public class Filtro implements Filter {

    private String urlSolicitada;
    private HttpSession session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init is call");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        this.urlSolicitada = ((HttpServletRequest) request).getRequestURI();

        this.session = ((HttpServletRequest) request).getSession();


        if (urlSolicitada.contains("/administrador/")) {
            filtroAdministrador(request, response, chain);
        } else if (urlSolicitada.contains("/professor/")) {
            filtroProfessor(request, response, chain);
        } else if (urlSolicitada.contains("/estoque/") && !urlSolicitada.equals("/fisiotec/estoque/login.xhtml") && !urlSolicitada.equals("/fisiotec/estoque/resources/imagens/stethoscope.png")) {
            filtroAdministradorEstoque(request, response, chain);
        } else if (urlSolicitada.contains("/estoque/administracao/")) {
            filtroAdministradorEstoquePrivilegios(request, response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy is call");
    }

    private void filtroAdministradorEstoquePrivilegios(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (session == null) {
            //sessão nula
            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/login.xhtml");
            chain.doFilter(request, response);
        } else {
            //sessao n nula
            BeanPortal portal = ((BeanPortal) ((HttpServletRequest) request).getSession(false).getAttribute("portalGenerico"));
            BeanEstoque beanAdiminEstoque = ((BeanEstoque) ((HttpServletRequest) request).getSession(false).getAttribute("beanAdiminEstoque"));
            if (beanAdiminEstoque == null) {
                //portal nulo
                beanAdiminEstoque = new BeanEstoque();
                beanAdiminEstoque.setFazendoBobagem(true);
                beanAdiminEstoque.setMensagemDaBobagem("Voce não está logado");
                ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/login.xhtml");
                System.out.println("-------------bean portal nulo");
                chain.doFilter(request, response);
            } else {
                //portal n nulo
                if (beanAdiminEstoque.getAdministrador().getId() > 0) {
                    if (beanAdiminEstoque.getAdministrador().isPrivilegios()) {
                        //user logado e com privilégios
                        chain.doFilter(request, response);
                    } else {
                        beanAdiminEstoque.setFazendoBobagem(true);
                        beanAdiminEstoque.setMensagemDaBobagem("Voce não estes privilégios");
                        ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                        ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/inicio.xhtml");
                        chain.doFilter(request, response);
                    }

                } else {
                    //user n logado
                    if (portal != null && portal.getAdministrador().getId() > 0) {
                        //administrador logado
                        portal.setFazendoBobagem(true);
                        portal.setMensagemDaBobagem("Voce não está logado como administrador do estoque, logo não terá acesso a área requisitada");
                        ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                        ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/administrador/inicio.xhtml");
                        chain.doFilter(request, response);
                    } else {
                        //admin n logado
                        if (beanAdiminEstoque != null && beanAdiminEstoque.getUsuario().getId() > 0) {
                            //admin estoque logado
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado como administrador do estoque, logo não terá acesso a área requisitada");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/inicio.xhtml");
                            chain.doFilter(request, response);
                        } else {
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/login.xhtml");
                            chain.doFilter(request, response);
                        }
                    }
                }
            }
        }
    }

    private void filtroProfessor(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (session == null) {
            //sessao nula
            ((HttpServletResponse) response).sendRedirect(Url.local + "portal/login.xhtml");
            chain.doFilter(request, response);
        } else {
            //sessao n nula
            BeanPortal portal = ((BeanPortal) ((HttpServletRequest) request).getSession(false).getAttribute("portalGenerico"));
            BeanEstoque beanAdiminEstoque = ((BeanEstoque) ((HttpServletRequest) request).getSession(false).getAttribute("beanAdiminEstoque"));
            if (portal == null) {
                //bean portal é nulo
                //redireciona
                portal = new BeanPortal();
                portal.setFazendoBobagem(true);
                portal.setMensagemDaBobagem("Voce não está logado");
                ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                ((HttpServletResponse) response).sendRedirect(Url.local + "portal/login.xhtml");
                chain.doFilter(request, response);
            } else {
                //portal já instanciado
                if (portal.getProfessor().getId() > 0) {
                    //se prof logado vai!
                    chain.doFilter(request, response);
                } else {
                    if (portal.getAdministrador().getId() > 0) {
                        //admin logado
                        portal.setFazendoBobagem(true);
                        portal.setMensagemDaBobagem("Voce não está logado como professor, logo não terá acesso a área requisitada");
                        ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                        ((HttpServletResponse) response).sendRedirect(Url.local + "portal/administrador/inicio.xhtml");
                        chain.doFilter(request, response);
                    } else {
                        //admin n logado
                        if (beanAdiminEstoque != null && beanAdiminEstoque.getUsuario().getId() > 0) {
                            //admin estoque logado
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado como administrador, logo não terá acesso a área requisitada");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/inicio.xhtml");
                            chain.doFilter(request, response);
                        } else {
                            //ng logado
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "portal/login.xhtml");
                            chain.doFilter(request, response);
                        }
                    }
                }
            }
        }
    }

    private void filtroAdministradorEstoque(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (session == null) {
            //sessão nula
            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/login.xhtml");
            chain.doFilter(request, response);
        } else {
            //sessao n nula
            BeanPortal portal = ((BeanPortal) ((HttpServletRequest) request).getSession(false).getAttribute("portalGenerico"));
            BeanEstoque beanAdiminEstoque = ((BeanEstoque) ((HttpServletRequest) request).getSession(false).getAttribute("beanAdiminEstoque"));
            if (beanAdiminEstoque == null) {
                //portal nulo
                beanAdiminEstoque = new BeanEstoque();
                beanAdiminEstoque.setFazendoBobagem(true);
                beanAdiminEstoque.setMensagemDaBobagem("Voce não está logado");
                ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/login.xhtml");
                chain.doFilter(request, response);
            } else {
                //portal n nulo
                if (beanAdiminEstoque.getAdministrador().getId() > 0) {
                    //user logado
                    try {
                        chain.doFilter(request, response);
                    } catch (IllegalStateException e) {
                        System.out.println("- - -- - acho q tá logado e o user tentou acessar url de adminestoque inválida, então n chjama o chain");
                    }
                } else {
                    //user n logado
                    if (portal != null && portal.getAdministrador().getId() > 0) {
                        //administrador logado
                        portal.setFazendoBobagem(true);
                        portal.setMensagemDaBobagem("Voce não está logado como administrador do estoque, logo não terá acesso a área requisitada");
                        ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                        ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/administrador/inicio.xhtml");
                        chain.doFilter(request, response);
                    } else {
                        //admin n logado
                        if (beanAdiminEstoque != null && beanAdiminEstoque.getUsuario().getId() > 0) {
                            //admin estoque logado
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado como administrador do estoque, logo não terá acesso a área requisitada");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/inicio.xhtml");
                            chain.doFilter(request, response);
                        } else {
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/login.xhtml");
                            try {
                                chain.doFilter(request, response);
                            } catch (IllegalStateException e) {
                                System.out.println("filtroAdministradorEstoque IllegalStateException");
                            }

                        }
                    }
                }
            }
        }
    }

    private void filtroAdministrador(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (session == null) {
            //se sessão nula reendereca pro login
            ((HttpServletResponse) response).sendRedirect(Url.local + "portal/login.xhtml");
            chain.doFilter(request, response);
        } else {
            //sessão n nulla
            BeanPortal portal = ((BeanPortal) ((HttpServletRequest) request).getSession(false).getAttribute("portalGenerico"));
            BeanEstoque beanAdiminEstoque = ((BeanEstoque) ((HttpServletRequest) request).getSession(false).getAttribute("beanAdiminEstoque"));
            if (portal == null) {
                //portal nulo
                portal = new BeanPortal();
                portal.setFazendoBobagem(true);
                portal.setMensagemDaBobagem("Voce não está logado");
                ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                ((HttpServletResponse) response).sendRedirect(Url.local + "portal/login.xhtml");
                chain.doFilter(request, response);
            } else {
                //bean portal não nulo
                if (portal.getAdministrador().getId() > 0) {
                    //admin logado
                    chain.doFilter(request, response);
                } else {
                    if (portal.getProfessor().getId() > 0) {
                        //professor logado
                        portal.setFazendoBobagem(true);
                        portal.setMensagemDaBobagem("Voce não está logado como administrador, logo não terá acesso a área requisitada");
                        ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                        ((HttpServletResponse) response).sendRedirect(Url.local + "portal/professor/inicio.xhtml");
                        chain.doFilter(request, response);
                    } else {
                        if (beanAdiminEstoque != null && beanAdiminEstoque.getUsuario().getId() > 0) {
                            //estoque logado
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado como administrador, logo não terá acesso a área requisitada");
                            ((HttpServletResponse) response).sendRedirect(Url.local + "estoque/inicio.xhtml");
                            chain.doFilter(request, response);
                        } else {
                            //n está logado
                            portal.setFazendoBobagem(true);
                            portal.setMensagemDaBobagem("Voce não está logado");
                            ((HttpServletRequest) request).getSession(false).setAttribute("portalGenerico", portal);
                            ((HttpServletResponse) response).sendRedirect(Url.local + "portal/login.xhtml");
                            try {
                                chain.doFilter(request, response);
                            } catch (IllegalStateException e) {
                                System.out.println("- - -- - acho q n tá logado e o user tentou acessar url de admin inválida, então n chjama o chain");
                            }

                        }
                    }
                }
            }
        }
    }
}
