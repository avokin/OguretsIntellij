// Copyright 2000-2018 JetBrains s.r.o.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package cd.connect.idea.plugins.cucumber.dart.steps.search;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import com.jetbrains.lang.dart.psi.DartMethodDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.CucumberUtil;
import cd.connect.idea.plugins.cucumber.dart.CucumberDartUtil;

public class CucumberJavaStepDefinitionSearch implements QueryExecutor<PsiReference, ReferencesSearch.SearchParameters> {
  @Override
  public boolean execute(@NotNull final ReferencesSearch.SearchParameters queryParameters,
                         @NotNull final Processor<? super PsiReference> consumer) {
    final PsiElement myElement = queryParameters.getElementToSearch();

    if (myElement.getParent() instanceof DartMethodDeclaration) {
      DartMethodDeclaration dc = (DartMethodDeclaration)myElement.getParent();

      String regexp = CucumberDartUtil.findDartAnnotationText(dc);

      if (regexp != null) {
        return CucumberUtil.findGherkinReferencesToElement(myElement, regexp, consumer, queryParameters.getEffectiveSearchScope());
      }
    }

    return true;

//    if (!(myElement instanceof PsiMethod)) {
//      return true;
//    }
//    final PsiMethod method = (PsiMethod)myElement;
//    Boolean isStepDefinition = ReadAction.compute(() -> CucumberDartUtil.isStepDefinition(method));
//    if (!isStepDefinition) {
//      return true;
//    }
//
//    final PsiAnnotation stepAnnotation =
//      ReadAction.compute(() -> CucumberDartUtil.getCucumberStepAnnotation(method));
//
//    final String regexp = CucumberDartUtil.getPatternFromStepDefinition(stepAnnotation);
//    if (regexp == null) {
//      return true;
//    }
//
  }
}
