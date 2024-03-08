import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { HashRouter, Route, Routes } from 'react-router-dom';
import Header from './header/Header';
import { ToastContainer } from 'react-toastify';
import ErrorBoundary from './error/ErrorBoundary';
import ErrorPage from './error/ErrorPage';
import MainPage from './components/MainPage.tsx';
import OtherPage from './components/OtherPage.tsx';

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <ToastContainer/>
        <HashRouter>
            <Header/>
            <ErrorBoundary>
                <Routes>
                    <Route path="/" element={<MainPage/>}/>
                    <Route path="/other" element={<OtherPage/>}/>
                    <Route path="*" element={<ErrorPage statusCode={400} errorMessage={'Page not found'}/>}/>
                </Routes>
            </ErrorBoundary>
        </HashRouter>
    </React.StrictMode>
);
