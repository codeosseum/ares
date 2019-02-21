(function matchmakingIIFE() {
    document.getElementById('find-match-button').addEventListener('click', findMatch)

    let intervalHandle;

    let ws;

    async function checkForMatch() {
        const response = await fetch('/api/matchmaking', {
            method: 'GET'
        });

        if (response.status == 204) {
            console.log('no match');
        } else {
            const match = await response.json();

            console.log(match);

            const uri = `ws://${match.server.uri.split('://')[1]}/ws`;

            ws = new WebSocket(uri);

            ws.addEventListener('open', () => {
                const hello = {
                    action: 'hello',
                    payload: {
                        username: window.sessionStorage.getItem('username')
                    }
                };

                ws.send(JSON.stringify(hello));
            })

            ws.addEventListener('message', event => {
                console.log(JSON.parse(event.data));
            })

            clearInterval(intervalHandle);
        }
    }

    async function findMatch() {
        const response = await fetch('/api/matchmaking/two-player-fault-seeding', {
            method: 'POST'
        });

        if (response.ok) {
            intervalHandle = setInterval(checkForMatch, 5000);
        } else {
            const failure = await response.json();

            console.log(failure);
        }
    }
})();
