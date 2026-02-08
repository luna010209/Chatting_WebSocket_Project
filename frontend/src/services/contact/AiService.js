import axios from "axios"

export const aiChat = (client, requestDto)=>{
    return client.current.publish({
        destination: "/app/ai-chat", 
        body: JSON.stringify(requestDto),
    })
} 

export const aiChatHistory= (token)=>{
    return axios.get(`/ai-chat`, {
        headers: {
            Authorization: `Bearer ${token}`,
        }
    })
}




