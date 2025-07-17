import React, { useState,useContext } from 'react';
import { Link, useLocation} from 'react-router-dom';
import styled from 'styled-components';
import { MenuContext } from 'js/component/MenuContext';

const PageTitle = () => {
  const menuList = useContext(MenuContext);
  const location = useLocation();
  const currentPath = location.pathname;

  const findPageTitle = (menus, path) => {
   
    for (const menu of menus) {
      if (menu.path === path) return menu.name;
      if (menu.subMenu) {
        const found = findPageTitle(menu.subMenu, path);
        if (found) return found;
      }
    }
    return null;
  };

  const pageTitle = findPageTitle(menuList, currentPath);

  return <h1>{pageTitle || '페이지 제목 없음'}</h1>;
};

// const MenuItem = ({ item}) => {
//   const [isOpen, setIsOpen] = useState(false);
//   return (
//     <div onMouseEnter={() => setIsOpen(true)} onMouseLeave={() => setIsOpen(false)}>
//       {item.subMenu ? (
//         <span>{item.name}</span>
//       ) : (
//         <MenuLink  to={item.path || '#'}>{item.name}</MenuLink >
//       )}
//       {item.subMenu && isOpen && (
//         <ul>
//           {item.subMenu.map((subItem, idx) => (
//             <li key={idx}>
//               <MenuItem item={subItem} />
//             </li>
//           ))}
//         </ul>
//       )}
//     </div>
//   );
// };

const Menu = ({ sort, menuClass }) => {
    const menuList = useContext(MenuContext);
    const viewMenuList = menuList.length > 0
        ? sort === 'admin'
        ? menuList.filter(menu => menu.path.startsWith('/admin'))
        : menuList.filter(menu => !menu.path.startsWith('/admin'))
        : [];

    if(sort ==='admin'){
        return (
            <ul className={menuClass}>
            {viewMenuList.map((item, index) => (
                <li key={index}>
                <MenuLink  to={item.path || '#'}>{item.name}</MenuLink >
                </li>
            ))}
            </ul>
        );
    }
      return (
            <ul className={menuClass}>
            {viewMenuList.map((item, index) => (
                <li key={index}>
                <Link  to={item.path || '#'}>{item.name}</Link >
                </li>
            ))}
            </ul>
        );

    };

const MenuLink = styled(Link)`
  color: white;
  font-size: 16px;
  text-decoration: none;

  &:hover {
    color: #ddd;
  }
`;

export {Menu,PageTitle}