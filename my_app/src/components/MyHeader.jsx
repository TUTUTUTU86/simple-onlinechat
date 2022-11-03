
function MyHeader(props){

    return (  
    <div style={{
        position: 'fixed',
        top: '0',
        left: '0',
        right: '0',
        height:'100px',
        backgroundColor: 'white',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1
    }}>
        <div style={{
            position: 'absolute',
            width: '300px',
            height: '50px',
            borderRadius: '150px',
            border: '2px solid black',
            display: 'flex',
            alignItems:'center',
            justifyContent: 'center'
        }}>
            <div style={{
                fontWeight: 'bold',
                fontSize: '20px'
            }}>POST!</div>
        </div>
       

      
    </div>);

}

export default MyHeader;