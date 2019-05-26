(function faultSeedingIIFE() {
    const el = selector => document.querySelector(selector);

    const match = JSON.parse(window.sessionStorage.getItem('match'));
    const username = window.sessionStorage.getItem('username');

    const ID_ALPHABET = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const ID_LENGTH = 24;

    let ws;

    let tickHandle;

    const STARTER_CODE = 'module.exports = function test(solution) {\n\n};\n';

    const Actions = Object.freeze({
        HELLO: 'hello',
        INVALID_TASK: 'fault-seeding-invalid-task',
        EVALUATION_ERROR: 'fault-seeding-evaluation-error',
        ERRONEOUS_SUBMISSION: 'fault-seeding-erroneous-submission',
        INCORRECT_SUBMISSION: 'fault-seeding-incorrect-submission',
        CORRECT_SUBMISSION: 'fault-seeding-correct-submission',
        TASK_STEP: 'fault-seeding-task-step',
        SUBMISSION_NOT_ACCEPTED: 'fault-seeding-submission-not-accepted',
        POST_SUBMISSION: 'post-fault-seeding-submission',
        MATCH_STARTING: 'fault-seeding-match-starting',
        MATCH_OVER: 'fault-seeding-match-over'
    });

    const messageHandlers = Object.freeze({
        [Actions.EVALUATION_ERROR]: handleEvaluationError,
        [Actions.ERRONEOUS_SUBMISSION]: handleErroneousSubmission,
        [Actions.INCORRECT_SUBMISSION]: handleIncorrectSubmission,
        [Actions.CORRECT_SUBMISSION]: handleCorrectSubmission,
        [Actions.TASK_STEP]: handleTaskStep,
        [Actions.MATCH_STARTING]: handleMatchStarting,
        [Actions.MATCH_OVER]: handleMatchOver
    });

    function handleEvaluationError() {
        appendOutput('\nEVALUATION ERROR\n\nReason: An error occurred when evaluating your submission.\n');
    };

    function handleErroneousSubmission({ payload }) {
        appendOutput('\nERRONEOUS SUBMISSION\n\nReason: An error occurred when evaluating your submission.\nDetails:\n' + payload.output + '\n');
    };

    function handleIncorrectSubmission({ payload }) {
        appendOutput('\nTEST CASE FAILURE\n\nReason: Correct and Under Test output are the same\nOutput: ' + payload.output + '\n');
    };

    function handleCorrectSubmission({ payload }) {
        appendOutput('\nTEST CASE SUCCESS\n\nReason: Different Correct and Under Test output found!\nExpected: ' + payload.expectedOutput + '\nActual: ' + payload.actualOutput + '\n');
    };

    const components = {
        overlay: {
            start: el('.start-overlay'),
            over: el('.over-overlay')
        },
        info: {
            points: el('.info > .points'),
            timer: el('.info > .timer'),
            place: el('.info > .place')
        },
        editors: {
            container: el('.editors'),
            specification: {
                container: el('.specification'),
                content: el('.specification-content > div'),
                mdTitle: () => el('.specification > .specification-content > div > h1:first-child')
            },
            underTestCode: {
                container: el('.under-test-code'),
                content: el('.under-test-code'),
            },
            testCode: {
                container: el('.test-code'),
                content: el('.test-code'),
            },
            output: el('.output')
        },
        finalScore: {
            title: el('.final-score-heading > h1'),
            table: el('.final-score-table > tbody')
        },
        output: el('.output'),
        leaderboard: el('.leaderboard'),
        submit: el('.submit-button'),
        quit: el('.quit-button')
    };

    const code = {
        underTestCode: null,
        testCode: null,
        output: null
    };

    const state = {
        match,
        username,
        ownScore: {
            position: 1,
            score: 0
        },
        scores: {},
        task: {
            id: '',
            title: '',
            description: '',
            code: ''
        },
        testCode: '',
        output: '',
        startTime: null,
        elapsedSeconds: 0,
        runtime: 0,
        isStarted: false
    };

    window.onload = function onLoad() {
        setupSplit();

        setupCodeMirror();

        setupWebSocket();

        setupSubmit();

        setupQuit();
    };

    function setupSplit() {
        Split([components.editors.container, components.output], {
            sizes: [75, 25],
            minSize: 40,
            direction: 'vertical'
        });

        Split([components.editors.specification.container, components.editors.underTestCode.container, components.editors.testCode.container], {
            sizes: [34, 33, 33],
            minSize: 0,
            direction: 'horizontal'
        });
    };

    function setupCodeMirror() {
        const defaultOptions = {
            value: '',
            mode:  "javascript",
            lineNumbers: true,
            scrollbarStyle: 'simple',
        };

        code.underTestCode = CodeMirror(components.editors.underTestCode.content, Object.assign({ readOnly: true }, defaultOptions));
        
        code.testCode = CodeMirror(components.editors.testCode.content, defaultOptions);

        code.output = CodeMirror(components.editors.output,
            Object.assign({ readOnly: true, lineNumbers: false, mode: null }, defaultOptions));

        code.testCode.on('change', onTestCodeChange);
    };

    function setupWebSocket() {
        const uri = `ws://${state.match.server.uri.split('://')[1]}/ws`;

        ws = new WebSocket(uri);

        ws.addEventListener('open', () => {
            console.log('WebSocket connection established.');

            login();
        });

        ws.addEventListener('message', event => {
            const message = JSON.parse(event.data);

            console.log('Message received', message);

            messageHandlers[message.action] && messageHandlers[message.action](message);
        });
    };

    function setupSubmit() {
        components.submit.addEventListener('click', onSubmitClick);
    };

    function setupQuit() {
        components.quit.addEventListener('click', onQuitClick);
    };

    function login() {
        console.log('Logging in');

        const hello = {
            action: Actions.HELLO,
            payload: {
                username: state.username,
                joinPassword: state.match.joinPassword
            }
        };

        send(hello);
    };

    function send(message) {
        console.log('Sending message', message);

        ws.send(JSON.stringify(message));
    };

    function onTestCodeChange(cm) {
        const testCode = cm.getDoc().getValue();

        setTestCode(testCode);
    };

    function onSubmitClick() {
        submitTestCode();
    };

    function onQuitClick() {
        window.sessionStorage.removeItem('match');

        window.location = '/game';
    };

    function submitTestCode() {
        const message = {
            action: Actions.POST_SUBMISSION,
            payload: {
                id: generateSubmissionId(),
                taskId: state.task.id,
                code: state.testCode
            }
        }

        console.log('Submitting test code.');

        send(message);
    };

    function generateSubmissionId() {
        // Thanks! https://stackoverflow.com/a/1349426
        let result = '';

        for (let i = 0; i < ID_LENGTH; i++ ) {
            result += ID_ALPHABET.charAt(Math.floor(Math.random() * ID_ALPHABET.length));
        }

        return result;
    };

    function handleTaskStep({ payload }) {
        setOwnScore(payload.scores);
        setScores(payload.scores);

        setTask(payload.nextTask);
        setTestCode(STARTER_CODE);

        appendOutput('NEW TASK\n\nTitle: ' + payload.nextTask.title + '\n')

        displayLeaderboard();
        displayPlace();
        displayPoints();
        displaySpecification();
        displayUnderTestCode();
        displayTestCode();
    };

    function handleMatchStarting({ payload }) {
        components.overlay.start.classList.add('hidden');

        state.runtime = payload.runtime;
        state.isStarted = true;
        state.startTime = Date.now();

        tickHandle = setInterval(tick, 1000);
    };

    function handleMatchOver({ payload }) {
        console.log(payload);

        state.finalScore = payload.finalScore;

        displayFinalScore();
    };

    function tick() {
        const elapsedMilliseconds = Date.now() - state.startTime;

        state.elapsedSeconds = Math.trunc(elapsedMilliseconds / 1000);

        if (state.elapsedSeconds > state.runtime) {
            clearInterval(tickHandle);
        } else {
            displayTimer();
        }
    };

    function displayTimer() {
        const remainingSeconds = state.runtime - state.elapsedSeconds;

        components.info.timer.textContent = formatTimeInSeconds(remainingSeconds);
    };

    function displayPoints() {
        components.info.points.textContent = formatPoints(state.ownScore.score);
    };

    function displayPlace() {
        components.info.place.textContent = formatPlace(state.ownScore.position);
    };

    function displayLeaderboard() {
        components.leaderboard.innerHTML = state.scores
            .map(formatScore)
            .join('');
    };

    function displaySpecification() {        
        components.editors.specification.content.innerHTML = marked(state.task.description);

        components.editors.specification.mdTitle().style.marginTop = 0;
    };

    function displayUnderTestCode() {
        code.underTestCode.getDoc().setValue(state.task.code);
    };

    function displayTestCode() {
        code.testCode.getDoc().setValue(state.testCode);
    };

    function displayFinalScore() {
        components.overlay.over.classList.remove('hidden');

        displayFinalScoreTitle();

        displayFinalScoreRows();
    };

    function displayFinalScoreTitle() {
        if (state.finalScore.result == 'DRAW') {
            components.finalScore.title.textContent = 'Draw'
        }

        if (state.finalScore.result == 'WIN') {
            if (state.finalScore.winners.includes(state.username)) {
                components.finalScore.title.textContent = 'Nicely done, you won!'
            } else {
                components.finalScore.title.textContent = 'Unfortunately, you lost.'
            }
        }
    };

    function displayFinalScoreRows() {
        const tableRows = state.finalScore.ranking
            .map((position, index) => {
                return position.players
                    .map(player => ({
                        place: index + 1,
                        username: player,
                        score: position.score
                    }));
            })
            .reduce((acc, curr) => acc.concat(curr), [])
            .map(({ place, username, score }) => `
                <tr>
                    <td>${place}.</td>
                    <td class="username-column">${username}</td>
                    <td>${score} points</td>
                </tr>
            `)
            .join('');
        
        components.finalScore.table.innerHTML = tableRows;
    };

    function formatScore(score, index) {
        return `
        <div class="leaderboard-entry">
            <div>#${index + 1} ${score.username} (${score.score})</div>
        </div>
        `;
    };

    function formatPoints(points) {
        return `${points} pts`;
    };

    function formatPlace(place) {
        switch (place) {
            case 1: return '1st';
            case 2: return '2nd';
            case 3: return '3rd';
            default: return `${place}th`;
        };
    }

    function setOwnScore(scores) {
        state.ownScore.score = scores[state.username];

        state.ownScore.position = sortScores(scores).findIndex(o => o.username == state.username) + 1;
    };

    function setScores(scores) {
        state.scores = sortScores(scores);
    };

    function setTask(task) {
        state.task = task;
    };

    function setTestCode(testCode) {
        state.testCode = testCode;
    };

    function appendOutput(text) {
        state.output += text;

        const doc = code.output.getDoc();

        doc.replaceRange(text, {
            line: doc.lastLine(),
            ch: 0
        });

        code.output.scrollIntoView({
            line: doc.lastLine(),
            ch: 0
        });
    };

    function sortScores(scores) {
        return Object.keys(scores)
            .map(k => ({ username: k, score: scores[k] }))
            .sort((a, b) => b.score - a.score);
    };

    function formatTimeInSeconds(time) {
        const SECONDS_IN_A_MINUTE = 60;
    
        const minutes = Math.floor(time / SECONDS_IN_A_MINUTE);
        const seconds = time - minutes * SECONDS_IN_A_MINUTE;
    
        const paddedMinutes = minutes >= 10 ? minutes : `0${minutes}`;
        const paddedSeconds = seconds >= 10 ? seconds : `0${seconds}`;
        
        return `${paddedMinutes}:${paddedSeconds}`;
    };
})();
