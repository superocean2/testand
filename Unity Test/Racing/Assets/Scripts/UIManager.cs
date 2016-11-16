using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class UIManager : MonoBehaviour
{

    int score;
    bool isGameover;
    public Text scoreText;
    public Button[] buttons;
    public AudioManager am;
    // Use this for initialization
    void Start()
    {
        score = 0;
        isGameover = false;
        InvokeRepeating("UpdateScore", 1f, 1f);
    }

    // Update is called once per frame
    void Update()
    {
        scoreText.text = "Score: " + score;
    }
    void UpdateScore()
    {
        if (!isGameover)
        {
            score++;
            am.makeScore.Play();
        }
    }
    public void SetGameOver()
    {
        isGameover = true;
        foreach (var button in buttons)
        {
            button.gameObject.SetActive(true);
        }
    }
    public void Play()
    {
        SceneManager.LoadScene("Main");
    }
    public void Pause()
    {
        if (Time.timeScale == 1)
        {
            Time.timeScale = 0;
            am.carsound.Pause();
        }
        else if (Time.timeScale == 0)
        {
            Time.timeScale = 1;
            am.carsound.Play();
        }
    }

    public void Replay()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
    public void Menu()
    {
        SceneManager.LoadScene("Menu");
    }
    public void Exit()
    {
        Application.Quit();
    }
}
