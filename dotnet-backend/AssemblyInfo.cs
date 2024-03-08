
using System.Reflection;

[assembly: AssemblyTitle("Hackathon .NET Demo and Template")]
[assembly: AssemblyCompany("Zühlke")]
[assembly: AssemblyVersion("1.0")]

#if DEBUG
    [assembly: AssemblyConfiguration("DEBUG"),]
#elif RELEASE
    [assembly: AssemblyConfiguration("RELEASE")]
#else
    #error "DEBUG or RELEASE not specified"
#endif
