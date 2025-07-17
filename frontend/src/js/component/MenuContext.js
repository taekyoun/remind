import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const MenuContext = createContext([]);

export const MenuProvider = ({ children }) => {
  const [menuList, setMenuList] = useState([]);

  useEffect(() => {
    axios.get('/api/menu/use')
      .then(({ data }) => setMenuList(data || []))
      .catch(() => console.log('메뉴정보를 가져오는데 실패하였습니다'));
  }, []);

  return (
    <MenuContext.Provider value={menuList}>
      {children}
    </MenuContext.Provider>
  );
};