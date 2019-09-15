#include <bits/stdc++.h>
        using namespace std;


        void compute_z(string s, int z[])
        {
        int l = 0, r = 0;
        int n = s.length();
        for (int i = 1; i <= n - 1; i++) {
        if (i > r) {
        l = i, r = i;
        while (r < n && s[r - l] == s[r])
        r++;
        z[i] = r - l;
        r--;
        }
        else {
        int k = i - l;
        if (z[k] < r - i + 1) {
        z[i] = z[k];
        }
        else {
        l = i;
        while (r < n && s[r - l] == s[r])
        r++;
        z[i] = r - l;
        r--;
        }
        }
        }
        }


        int countPermutation(string a, string b)
        {
        b = b + b;


        b = b.substr(0, b.size() - 1);

        int ans = 0;
        string s = a + "$" + b;
        int n = s.length();

        int z[n];
        compute_z(s, z);

        for (int i = 1; i <= n - 1; i++) {

        if (z[i] == a.length())
        ans++;
        }
        return ans;
        }


        int main()
        {
        string a,b;
        int t ;
        cin>>t ;
        while(t--)
        {
        cin>>a>>b ;


        cout << countPermutation(a, b) << endl;
        }
        return 0;
        }
