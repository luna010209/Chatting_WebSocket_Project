export const aiChat = (client, requestDto)=>{
    return client.current.publish({
        destination: "/app/ai-chat", 
        body: JSON.stringify(requestDto),
    })
} 




