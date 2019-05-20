(function matchmakingIIFE() {
    document.querySelector('.action-button.find-fault-seeding-match')
        .addEventListener('click', findFaultSeedingMatch);

    document.querySelector('.accept-match-button')
        .addEventListener('click', acceptMatch);

    const username = window.sessionStorage.getItem('username');
    let match = null;

    let intervalHandle;

    let ws;

    async function checkForMatch() {
        const response = await fetch('/api/matchmaking', {
            method: 'GET'
        });

        if (response.status == 204) {
            console.log('no match yet');
        } else {
            match = await response.json();

            console.log(match);

            matchFound();
        }
    };

    async function matchFound() {
        document.querySelector('.finding-match-status').classList.add('hidden');
        document.querySelector('.match-found-status').classList.remove('hidden');
        document.querySelector('.accept-match-button').classList.remove('visibility-hidden');

        clearInterval(intervalHandle);
    };

    async function acceptMatch() {
        window.sessionStorage.setItem('match', JSON.stringify(match));
        
        window.location = '/game/fault-seeding'
        /*const uri = `ws://${match.server.uri.split('://')[1]}/ws`;

        ws = new WebSocket(uri);

        ws.addEventListener('open', () => {
            const hello = {
                action: 'hello',
                payload: {
                    username
                }
            };

            ws.send(JSON.stringify(hello));
        })

        ws.addEventListener('message', event => {
            console.log(JSON.parse(event.data));
        })*/
    };

    async function findFaultSeedingMatch() {
        document.querySelector('.overlay').classList.remove('hidden');
        document.querySelector('.finding-match-status').classList.remove('hidden');
        document.querySelector('.match-found-status').classList.add('hidden');
        document.querySelector('.accept-match-button').classList.add('visibility-hidden');

        const response = await fetch('/api/matchmaking/two-player-fault-seeding', {
            method: 'POST'
        });

        if (response.ok) {
            intervalHandle = setInterval(checkForMatch, 5000);
        } else {
            const failure = await response.json();

            console.log(failure);
        }
    };
})();
