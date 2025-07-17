import React, {useState,useEffect} from 'react'
import axios from 'axios'
import styled from "styled-components"

const BoardWrapper = styled.div`
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
`;

const BoardTitle = styled.h3`
  font-size: 2rem;
  margin-bottom: 20px;
  font-weight: bold;
  color: #333;
`;

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3열 */
  gap: 20px; /* 아이템 간격 */
`;

const Card = styled.div`
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 5px rgb(0 0 0 / 0.1);
  background-color: #fff;
  display: flex;
  flex-direction: column;
`;

const Thumbnail = styled.img`
  width: 100%;
  height: 180px;
  object-fit: cover;
`;

const Content = styled.div`
  padding: 10px 15px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
`;

const Title = styled.h4`
  font-size: 1.1rem;
  margin: 0 0 8px 0;
  color: #222;
  flex-grow: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const DateText = styled.span`
  font-size: 0.9rem;
  color: #777;
  text-align: right;
`;

// 임시 이미지 URL 예시용
const sampleImageUrl =
  "https://via.placeholder.com/300x180.png?text=Thumbnail";

  const dummyData = Array.from({ length: 9 }, (_, i) => ({
  id: i + 1,
  title: `게시물 제목 ${i + 1}`,
  image: `https://via.placeholder.com/300x200?text=Image+${i + 1}`,
  createdAt: '2025-07-17',
}));

const Board = ()=>{
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchData = async () =>{
        setError(null);
        setLoading(true);
        try {
            const response = await axios.get('/api/board');
            setData(response.data)
        } catch (error) {
            setError(error)
        }
        finally{
            setLoading(false)
        }
    }
    
    useEffect(()=>{
          setData(dummyData)
           setLoading(false)
        // fetchData();
    },[])
    
    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const day = date.getDate().toString().padStart(2, "0");
        return `${month}.${day}`;
    };

    return (
        <BoardWrapper>
      <BoardTitle>게시판</BoardTitle>

      {loading && <p>로딩중...</p>}
      {error && <p>에러 발생: {error.message}</p>}

      {!loading && !error && (
        <GridContainer>
          {data.map((item) => (
            <Card key={item.id}>
              {/* 이미지가 없으면 임시 이미지 */}
              <Thumbnail src={item.imageUrl || sampleImageUrl} alt={item.title} />
              <Content>
                <Title title={item.title}>{item.title}</Title>
                <DateText>{formatDate(item.createdAt)}</DateText>
              </Content>
            </Card>
          ))}
        </GridContainer>
      )}
    </BoardWrapper>
    )
}

export default Board