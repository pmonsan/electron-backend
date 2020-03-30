/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonDocumentDetailComponent } from 'app/entities/person-document/person-document-detail.component';
import { PersonDocument } from 'app/shared/model/person-document.model';

describe('Component Tests', () => {
  describe('PersonDocument Management Detail Component', () => {
    let comp: PersonDocumentDetailComponent;
    let fixture: ComponentFixture<PersonDocumentDetailComponent>;
    const route = ({ data: of({ personDocument: new PersonDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
