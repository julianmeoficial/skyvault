import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import About from './pages/About'
import { ComparePage } from './pages/ComparePage'
import ComparisonSelectorPage from './pages/ComparisonSelector'
import { AircraftCatalog } from './pages/AircraftCatalog'
import { AircraftDetailPage } from './pages/AircraftDetail'
import News from './pages/News/News'
import NewsDetail from './pages/NewsDetail/NewsDetail'
import Privacy from './pages/Privacy/Privacy';
import Terms from './pages/Terms/Terms';
import DataSources from './pages/DataSources/DataSources';
import Cookies from './pages/Cookies/Cookies';
import FAQ from './pages/FAQ/FAQ';
import Contact from './pages/Contact/Contact';
import APIpage from './pages/APIPage/APIPage';
import Manufactures from "./pages/Manufacturers/Manufacturers";
import Families from './pages/Families/FamiliesPage';
import './App.css'

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/about" element={<About />} />
                <Route path="/aircraft" element={<AircraftCatalog />} />
                <Route path="/catalog" element={<AircraftCatalog />} />
                <Route path="/aircraft/:identifier" element={<AircraftDetailPage />} />
                <Route path="/compare-select" element={<ComparisonSelectorPage />} />
                <Route path="/compare" element={<ComparePage />} />
                <Route path="/news" element={<News />} />
                <Route path="/news/:id" element={<NewsDetail />} />
                <Route path="/privacy" element={<Privacy />} />
                <Route path="/terms" element={<Terms />} />
                <Route path="/sources" element={<DataSources />} />
                <Route path="/cookies" element={<Cookies />} />
                <Route path="/faq" element={<FAQ />} />
                <Route path="/contact" element={<Contact />} />
                <Route path="/api" element={<APIpage />} />
                <Route path="/manufacturers" element={<Manufactures />} />
                <Route path="/families" element={<Families />} />
            </Routes>
        </Router>
    )
}

export default App
