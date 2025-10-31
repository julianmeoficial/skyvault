import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from '../../shared/components/layout/Header';

const MainLayout: React.FC = () => {
    return (
        <>
            <Header />
            <Outlet /> {/* Aquí se renderiza Home, Compare, News, etc. */}
        </>
    );
};

export default MainLayout;
