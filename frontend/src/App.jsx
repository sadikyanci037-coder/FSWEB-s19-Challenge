import { useEffect, useState } from "react";

function App() {
  const [tweets, setTweets] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:3000/tweet/findByUserId?userId=2", {
      headers: {
        Authorization: "Basic " + btoa("sadik2:1234"),
      },
    })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Tweetler alınamadı.");
          }

          return response.json();
        })
        .then((data) => {
          setTweets(data);
        })
        .catch((err) => {
          console.error(err);
          setError(err.message);
        });
  }, []);

  return (
      <div>
        <h1>Twitter API</h1>

        {error && <p>{error}</p>}

        {tweets.map((tweet) => (
            <div key={tweet.id}>
              <h3>{tweet.user.username}</h3>
              <p>{tweet.content}</p>
            </div>
        ))}
      </div>
  );
}

export default App;