import React, { Suspense} from 'react';
import { Link } from 'react-router-dom';
import {Menu, PageTitle} from 'js/component/Menu';
import ComponentRoute from 'js/component/Route';
import { MenuProvider  } from 'js/component/MenuContext';
// import style from 'css/tiles/Portal.module.css'
import styled from 'styled-components';

const Portal = ()=> {
    return (
         <Layout>
             <MenuProvider>
                <Header>
                    <HeaderLeft>
                        <PageTitle />
                    </HeaderLeft>
                    <HeaderRight>
                        <GoAdminPage />
                    </HeaderRight>
                </Header>

                <Main>
                    <Sidebar>
                            <Menu sort="portal" menuClass="menu_item" />
                    </Sidebar>

                    <Content>
                        <Suspense fallback={<div>로딩 중...</div>}>
                            <ComponentRoute />
                        </Suspense>
                    </Content>
                </Main>

                <Footer>ⓒ 2025 Your Company</Footer>
            </MenuProvider>
        </Layout>
    )
}

const GoAdminPage = ({ isAdmin }) => {
//   if (!isAdmin) return null;

  return <AdminLink to="/admin/menuMng">관리자</AdminLink>;
};


const Layout = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  font-family: 'Pretendard', sans-serif;
`;

const Header = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #1f2937;
  color: white;
  padding: 12px 24px;
  height: 60px;
`;

const HeaderLeft = styled.div`
  font-size: 1.2rem;
  font-weight: bold;
`;

const HeaderRight = styled.div`
  display: flex;
  align-items: center;
`;

const Main = styled.main`
  display: flex;
  flex: 1;
  overflow: hidden;
`;

const Sidebar = styled.nav`
  width: 200px;
  background-color: #f3f4f6;
  padding: 16px;
  border-right: 1px solid #e5e7eb;
`;

const Content = styled.div`
  flex: 1;
  padding: 24px;
  overflow-y: auto;
`;

const Footer = styled.footer`
  height: 50px;
  background-color: #1f2937;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.9rem;
`;
const AdminLink = styled(Link)`
  color: #fff;
  background: #2563eb;
  padding: 6px 12px;
  border-radius: 6px;
  text-decoration: none;
  font-size: 0.85rem;

  &:hover {
    background: #1e40af;
  }
`;

export default Portal