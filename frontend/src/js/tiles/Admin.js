import React, { Suspense} from 'react';
import { Link } from 'react-router-dom';
import {Menu, PageTitle} from 'js/component/Menu';
import ComponentRoute from 'js/component/Route';
import { MenuProvider  } from 'js/component/MenuContext';
import styled from 'styled-components';
// import style from 'css/tiles/Admin.module.css'

const Admin = ()=>{
    return (
    <Container>
        <MenuProvider>
            <Header>
                <GoPortalPage />
                <PageTitle />
            </Header>
            <Nav>
                <Menu sort="admin" menuClass="menu-item" />
            </Nav>
            <ContentBody>
                <Suspense fallback={<div>로딩 중...</div>}>
                <ComponentRoute />
                </Suspense>
            </ContentBody>
            <Footer />
        </MenuProvider>
    </Container>
    )
}

const GoPortalPage = () => (
  <PortalLink>
    <Link to="/">포털</Link>
  </PortalLink>
);


const Container = styled.div`
  display: grid;
  grid-template-areas:
    "header header"
    "nav content"
    "footer footer";
  grid-template-columns: 200px 1fr;
  grid-template-rows: 60px 1fr 40px;
  height: 100vh;
`;

const Header = styled.header`
  grid-area: header;
  background-color: #2c3e50;
  color: white;
  display: flex;
  align-items: center;
  padding: 0 20px;
  justify-content: space-between;
`;

const Nav = styled.nav`
  grid-area: nav;
  background-color: #34495e;
  color: white;
  padding: 20px;
  overflow-y: auto;

  .menu-item {
    display: block;
    font-size: 22px;
    margin-bottom: 10px;
    text-decoration: none;
    padding: 10px;
    border-radius: 6px;
    transition: background 0.2s;
    color: white !important;
    text-decoration: none !important;
    &:hover {
      background: #087feeff;
    }
  }
`;

const ContentBody = styled.main`
  grid-area: content;
  padding: 20px;
  overflow-y: auto;
  background-color: #f4f6f8;
`;

const Footer = styled.footer`
  grid-area: footer;
  background-color: #2c3e50;
`;

const PortalLink = styled.div`
  a {
    color: white;
    text-decoration: none;
    font-weight: bold;
  }
`;

export default Admin