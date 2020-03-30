/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonDocumentComponent } from 'app/entities/person-document/person-document.component';
import { PersonDocumentService } from 'app/entities/person-document/person-document.service';
import { PersonDocument } from 'app/shared/model/person-document.model';

describe('Component Tests', () => {
  describe('PersonDocument Management Component', () => {
    let comp: PersonDocumentComponent;
    let fixture: ComponentFixture<PersonDocumentComponent>;
    let service: PersonDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonDocumentComponent],
        providers: []
      })
        .overrideTemplate(PersonDocumentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonDocumentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonDocumentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonDocument(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
