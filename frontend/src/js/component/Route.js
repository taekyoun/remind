import {lazy} from 'react';
import { Route, Routes,useLocation} from 'react-router-dom';

const KeywordAnalsis = lazy(() => import  ('js/domain/analsis/KeywordAnalsis'));
const MenuMng = lazy(() => import  ('js/domain/admin/MenuMng'));
const CodingBoard = lazy(()=> import ('js/domain/board/CodingBoard'));
const Board = lazy(()=> import ('js/domain/board/Board'));
const Home = lazy(()=> import ('js/domain/Home'));
const About = lazy(()=> import ('js/domain/About'));

const componentMap = {
    '/': Home,
    '/about':About,
    '/news': KeywordAnalsis,
    '/board/coding': CodingBoard,
    '/admin/menuMng': MenuMng,
    '/board': Board,
    // Add more mappings here
};

const ComponentRoute = ()=>{
    const location = useLocation();
    const currentPath = location.pathname;
    const ComponentToRender = componentMap[currentPath] || null;
    return (
        <Routes>
            <Route path="/*" element={ComponentToRender ? <ComponentToRender  /> : <div>Page not found</div>} />
        </Routes>
    )
}

export default ComponentRoute;