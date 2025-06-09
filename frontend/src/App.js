import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Home from './pages/Home';
import About from './pages/About';
import Header from './components/Header';
import axios from 'axios';
import { useEffect, useState } from 'react';

function App() {
    const [message, setMessage] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8081/api/hello')
            .then(response => setMessage(response.data))
            .catch(error => console.error('Error fetching API:', error));
    }, []);

  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
      </Routes>
      <div>
        <h1>Spring Boot API 연결 테스트</h1>
        <p>서버에서 가져온 메시지: {message}</p>
      </div>
    </Router>
  )
 
  }
export default App;